# Jenkins Docker Compose Hatası - Hızlı Çözüm

## ❌ Hata
```
docker: not found
docker-compose: not found
```

**DURUM:** Jenkins container'ında Docker CLI yüklü değil!

## ✅ ÇÖZÜM: Jenkins Container'ına Docker CLI Yükle

### Adım 1: Jenkins Container'a Root Olarak Bağlan
```bash
docker exec -it -u root jenkins bash
```

### Adım 2: Docker CLI'yi Yükle
```bash
# Gerekli paketleri yükle
apt-get update
apt-get install -y apt-transport-https ca-certificates curl gnupg lsb-release

# Docker GPG anahtarını ekle
curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

# Docker repository ekle
echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null

# Docker CLI ve Compose plugin'ini yükle
apt-get update
apt-get install -y docker-ce-cli docker-compose-plugin

# Kontrol et
docker --version
docker compose version
```

### Adım 3: Docker Socket İzinlerini Düzenle
```bash
# Hala root kullanıcısı olarak
chmod 666 /var/run/docker.sock

# Jenkins kullanıcısını docker grubuna ekle
usermod -aG docker jenkins

# Container'dan çık
exit
```

### Adım 4: Jenkins Container'ı Yeniden Başlat
```bash
docker restart jenkins
```

## 🚀 Alternatif: Custom Jenkins Image Kullan (Önerilen)

### Dockerfile.jenkins Oluştur
```dockerfile
FROM jenkins/jenkins:lts

USER root

# Docker CLI yükle
RUN apt-get update && \
    apt-get install -y apt-transport-https ca-certificates curl gnupg lsb-release && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg && \
    echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null && \
    apt-get update && \
    apt-get install -y docker-ce-cli docker-compose-plugin && \
    apt-get clean

# Jenkins kullanıcısını docker grubuna ekle
RUN usermod -aG docker jenkins

USER jenkins
```

### Image Build ve Container Çalıştır
```bash
# Mevcut Jenkins container'ı durdur
docker stop jenkins
docker rm jenkins

# Yeni image oluştur
docker build -t jenkins-with-docker -f Dockerfile.jenkins .

# Yeni container çalıştır
docker run -d \
  --name jenkins \
  -p 8181:8080 \
  -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  jenkins-with-docker

# Docker socket izinlerini düzenle
docker exec -u root jenkins chmod 666 /var/run/docker.sock
```

## ✅ Önceki Çözüm (Artık Gerekli Değil)

Jenkinsfile'da `docker-compose` yerine `docker compose` (tire olmadan) kullanıldı.

### Değişiklik:
```diff
- docker-compose -f compose.yaml up -d
+ docker compose -f compose.yaml up -d
```

## 🔧 Eğer Hala Çalışmıyorsa

### Seçenek 1: Jenkins Container'a Docker Compose Plugin Yükle
```bash
docker exec -it -u root jenkins bash
mkdir -p /usr/local/lib/docker/cli-plugins
curl -SL https://github.com/docker/compose/releases/download/v2.24.0/docker-compose-linux-x86_64 -o /usr/local/lib/docker/cli-plugins/docker-compose
chmod +x /usr/local/lib/docker/cli-plugins/docker-compose
```

### Seçenek 2: Docker Socket İzinlerini Düzenle
```bash
docker exec -u root jenkins chmod 666 /var/run/docker.sock
```

### Seçenek 3: Jenkins Container'ı Docker Socket ile Yeniden Başlat
```bash
docker run -d \
  --name jenkins \
  -p 8181:8080 \
  -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  jenkins/jenkins:lts

# İzinleri düzenle
docker exec -u root jenkins chmod 666 /var/run/docker.sock
```

## 📚 Detaylı Açıklama

Daha fazla bilgi için `JENKINS_DOCKER_SETUP.md` dosyasına bakın.

## 🎯 Test Etme

Jenkins Container'ına bağlanıp test edin:

```bash
# Jenkins container'a bağlan (jenkins kullanıcısı olarak)
docker exec -it jenkins bash

# Docker komutlarını test et
docker --version
docker compose version
docker ps

# Compose test
docker compose -f /var/jenkins_home/workspace/OnlineEgitimVeSinavSurecleriYonetimSistemi/compose.yaml ps

# Container'dan çık
exit
```

## 📋 Hızlı Komutlar

### Tek Komutla Docker CLI Yükle
```bash
docker exec -u root jenkins bash -c "apt-get update && apt-get install -y apt-transport-https ca-certificates curl gnupg lsb-release && curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg && echo 'deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian bullseye stable' | tee /etc/apt/sources.list.d/docker.list > /dev/null && apt-get update && apt-get install -y docker-ce-cli docker-compose-plugin && chmod 666 /var/run/docker.sock && usermod -aG docker jenkins"

# Jenkins'i yeniden başlat
docker restart jenkins
```

## ⚠️ Önemli Notlar

1. **Docker Socket Mount**: Jenkins container'ı başlatırken `-v /var/run/docker.sock:/var/run/docker.sock` parametresini kullandığınızdan emin olun.

2. **İzinler**: Docker socket'ine Jenkins kullanıcısının erişimi olmalı: `chmod 666 /var/run/docker.sock`

3. **Grup Üyeliği**: Jenkins kullanıcısı docker grubuna eklenmeli: `usermod -aG docker jenkins`

4. **Yeniden Başlatma**: İzin değişikliklerinden sonra Jenkins container'ını yeniden başlatın: `docker restart jenkins`

## ✅ Push Durumu

- ✅ Jenkinsfile güncellendi
- ✅ GitHub'a push edildi
- ✅ Webhook tetiklenecek
- ✅ Pipeline yeniden çalışacak

## 🚀 Sonraki Adım

Jenkins'te yeni build'in başlamasını bekleyin. Docker komutları artık çalışmalı!

