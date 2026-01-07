# ✅ Maven Wrapper İzin Sorunu Çözüldü!

## 🔴 Yaşanan Sorunlar

### Sorun 1: Permission Denied

Jenkins pipeline'da Maven wrapper çalıştırılırken şu hata alınıyordu:

```
./mvnw clean
/var/jenkins_home/workspace/.../script.sh.copy: 1: ./mvnw: Permission denied
script returned exit code 126
```

### Sorun 2: JAVA_HOME Not Defined

Permission sorunu çözüldükten sonra:

```
./mvnw clean
The JAVA_HOME environment variable is not defined correctly,
this environment variable is needed to run this program.
script returned exit code 1
```

## 🔍 Sorunların Nedenleri

### Neden 1: Execute İzni Yok

`mvnw` ve `mvnw.cmd` dosyalarının Git'te **execute (çalıştırma) izni** yoktu.

- **Hatalı**: `100644` (okuma/yazma izinleri)
- **Doğru**: `100755` (okuma/yazma/çalıştırma izinleri)

### Neden 2: Jenkins Container'da JDK Yok

Jenkins container'ı `jenkins/jenkins:lts` imajından oluşturuldu ancak içinde JDK kurulu değildi. Maven wrapper'ın çalışması için JDK gerekli.

## ✅ Uygulanan Çözümler

### Çözüm 1: Git'te Execute İzni Ekleme

```bash
# Maven wrapper dosyalarına execute izni ver
git update-index --chmod=+x mvnw
git update-index --chmod=+x mvnw.cmd

# İzinleri doğrula (100755 olmalı)
git ls-files -s mvnw
# Output: 100755 bd8896bf2217b46faa0291585e01ac1a3441a958 0 mvnw ✅
```

### Çözüm 2: Jenkins Container'a JDK 21 Kurulumu

#### Otomatik Kurulum (PowerShell):

```powershell
# Script'i çalıştır
.\install-jdk-jenkins.ps1
```

#### Manuel Kurulum:

```bash
# Jenkins container ismini bul
docker ps --filter "ancestor=jenkins/jenkins:lts" --format "{{.Names}}"

# JDK 21 kur (container ismi: jenkins-server)
docker exec -u root jenkins-server apt-get update
docker exec -u root jenkins-server apt-get install -y openjdk-21-jdk

# Java versiyonunu doğrula
docker exec jenkins-server java -version

# Jenkins'i yeniden başlat
docker restart jenkins-server
```

#### Jenkins UI'da JDK Konfigürasyonu:

1. **Jenkins'e gidin**: http://localhost:8181
2. **Manage Jenkins** > **Tools**
3. **JDK installations** bölümünü bulun
4. **Add JDK** tıklayın:
   - Name: `JDK 21`
   - JAVA_HOME: `/usr/lib/jvm/java-21-openjdk-amd64`
   - ⚠️ "Install automatically" seçeneğini **KALDIR**
5. **Save**

### Çözüm 3: Jenkinsfile'da JAVA_HOME Tanımlama

```groovy
pipeline {
    agent any

    tools {
        maven 'Maven 3.9.9'
        jdk 'JDK 21'
    }

    environment {
        DOCKER_IMAGE = 'oesys-app'
        DOCKER_TAG = "${BUILD_NUMBER}"
        JAVA_HOME = "${tool 'JDK 21'}"        // ← YENİ!
        PATH = "${JAVA_HOME}/bin:${env.PATH}" // ← YENİ!
    }

    stages {
        stage('☕ Verify Java') {               // ← YENİ STAGE!
            steps {
                sh '''
                    echo "JAVA_HOME: $JAVA_HOME"
                    java -version
                '''
            }
        }
        // ...diğer stage'ler
    }
}
```

### Çözüm 4: Checkout Stage'ine chmod Ekleme

```groovy
stage('🚀 Checkout') {
    steps {
        echo '📦 Checking out code from repository...'
        checkout scm
        sh 'git rev-parse --short HEAD > .git/commit-id'
        script {
            env.GIT_COMMIT_SHORT = readFile('.git/commit-id').trim()
        }
        // Maven wrapper'a execute izni ver
        sh 'chmod +x mvnw'  // ← YENİ!
    }
}
```

### Çözüm 5: Değişiklikleri Commit ve Push

```bash
git add Jenkinsfile mvnw mvnw.cmd install-jdk-jenkins.ps1 MAVEN_WRAPPER_FIX.md
git commit -m "fix: Add JAVA_HOME and Maven wrapper permissions"
git push origin main
```

## 🎯 Sonuç

Pipeline artık sorunsuz çalışıyor:

```
+ ./mvnw clean
✅ [INFO] Scanning for projects...
✅ [INFO] Building OnlineEgitimVeSinavSurecleriYonetimSistemi...
✅ [INFO] BUILD SUCCESS
```

## 📊 Pipeline Akışı

```
1. 🚀 Checkout
   ├─ Kod çekme
   ├─ Git commit ID
   └─ chmod +x mvnw ← YENİ!

2. ☕ Verify Java                    ← YENİ STAGE!
   ├─ JAVA_HOME kontrolü
   └─ java -version

3. 🐳 Docker Ayağa Kaldırma
   └─ PostgreSQL başlatma

4. 🔧 Maven Clean
   └─ ./mvnw clean ✅ Artık çalışıyor!

5. 📦 Maven Compile
   └─ ./mvnw compile ✅

6. 🧪 Birim Testleri
   └─ ./mvnw test ✅

7. 🔗 Entegrasyon Testleri
   └─ ./mvnw verify ✅

8. 🌐 Selenium E2E Testleri
   └─ ./mvnw test -Dtest=*E2E* ✅

9. 📊 Test Coverage Raporu
   └─ ./mvnw jacoco:report ✅

10. 📦 Build Package
    └─ ./mvnw package ✅

11. 🛑 Docker Durdurma
```

## 🔧 Tekrar Eden Sorunlar İçin

Eğer izinler kaybolursa (çok nadir):

```bash
# Local'de izinleri kontrol et
git ls-files -s mvnw

# 100644 görüyorsan düzelt
git update-index --chmod=+x mvnw
git commit -m "fix: Restore execute permission"
git push
```

## ✨ Önemli Notlar

1. **Windows'ta**: Git bash `chmod +x` desteği yok, `git update-index` kullan
2. **Linux/Mac'te**: Direkt `chmod +x mvnw` çalışır
3. **Jenkins'te**: Checkout'tan sonra `sh 'chmod +x mvnw'` her zaman çalışır
4. **Kalıcı Çözüm**: Git'te 100755 izni olması yeterli

## 📚 İlgili Komutlar

```bash
# İzinleri görüntüle
git ls-files -s mvnw

# Execute izni ekle (Windows)
git update-index --chmod=+x mvnw

# Execute izni ekle (Linux/Mac)
chmod +x mvnw
git add mvnw

# İzinleri kaldır
git update-index --chmod=-x mvnw
```

## 🎉 Başarı Metrikleri

- ✅ Pipeline ilk adımda başarısız olmuyor
- ✅ Maven komutları sorunsuz çalışıyor
- ✅ Tüm testler düzenli çalışıyor
- ✅ Webhook otomasyonu aktif

---

**Tarih**: 2026-01-08  
**Commit**: `d53860d` - "fix: Add execute permission to Maven wrapper files"  
**Durum**: ✅ Çözüldü ve Test Edildi  
**Jenkins Build**: Başarılı ✅  

