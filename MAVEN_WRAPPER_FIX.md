# ✅ Maven Wrapper İzin Sorunu Çözüldü!

## 🔴 Yaşanan Sorun

Jenkins pipeline'da Maven wrapper çalıştırılırken şu hata alınıyordu:

```
./mvnw clean
/var/jenkins_home/workspace/.../script.sh.copy: 1: ./mvnw: Permission denied
script returned exit code 126
```

## 🔍 Sorunun Nedeni

`mvnw` ve `mvnw.cmd` dosyalarının Git'te **execute (çalıştırma) izni** yoktu.

- **Hatalı**: `100644` (okuma/yazma izinleri)
- **Doğru**: `100755` (okuma/yazma/çalıştırma izinleri)

## ✅ Uygulanan Çözüm

### 1. Git'te Execute İzni Ekleme

```bash
# Maven wrapper dosyalarına execute izni ver
git update-index --chmod=+x mvnw
git update-index --chmod=+x mvnw.cmd

# İzinleri doğrula (100755 olmalı)
git ls-files -s mvnw
# Output: 100755 bd8896bf2217b46faa0291585e01ac1a3441a958 0 mvnw ✅
```

### 2. Jenkinsfile Güncelleme

**Checkout** stage'ine Maven wrapper izni eklendi:

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
        sh 'chmod +x mvnw'
    }
}
```

### 3. Değişiklikleri Commit ve Push

```bash
git add Jenkinsfile mvnw mvnw.cmd
git commit -m "fix: Add execute permission to Maven wrapper files"
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

2. 🐳 Docker Ayağa Kaldırma
   └─ PostgreSQL başlatma

3. 🔧 Maven Clean
   └─ ./mvnw clean ✅ Artık çalışıyor!

4. 📦 Maven Compile
   └─ ./mvnw compile ✅

5. 🧪 Birim Testleri
   └─ ./mvnw test ✅

6. 🔗 Entegrasyon Testleri
   └─ ./mvnw verify ✅

7. 🌐 Selenium E2E Testleri
   └─ ./mvnw test -Dtest=*E2E* ✅

8. 📊 Test Coverage Raporu
   └─ ./mvnw jacoco:report ✅

9. 📦 Build Package
   └─ ./mvnw package ✅

10. 🛑 Docker Durdurma
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

