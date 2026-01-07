# Jenkins Docker Compose Kurulum Çözümü

## Sorun
Jenkins container'ında `docker-compose` veya `docker compose` komutu bulunamıyor.

## Çözüm 1: Docker Compose V2 Kullanımı (Önerilen)
Jenkinsfile'da `docker-compose` yerine `docker compose` kullanın (tire olmadan).

✅ Bu çözüm uygulandı!

```groovy
sh 'docker compose -f compose.yaml up -d'
```

## Çözüm 2: Jenkins Container'a Docker Compose Yükleme

Eğer Çözüm 1 işe yaramazsa, Jenkins container'ına Docker Compose'u yükleyin:

### Jenkins Container'a Bağlanma
```bash
docker exec -it -u root jenkins bash
```

### Docker Compose V2 Plugin Yükleme
```bash
# Docker Compose CLI plugin'ini yükle
mkdir -p /usr/local/lib/docker/cli-plugins
curl -SL https://github.com/docker/compose/releases/download/v2.24.0/docker-compose-linux-x86_64 -o /usr/local/lib/docker/cli-plugins/docker-compose
chmod +x /usr/local/lib/docker/cli-plugins/docker-compose

# Kontrol et
docker compose version
```

### Alternatif: Docker Compose Standalone Binary
```bash
curl -L "https://github.com/docker/compose/releases/download/v2.24.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose
ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose

# Kontrol et
docker-compose version
```

## Çözüm 3: Custom Jenkins Docker Image

Kendi Jenkins image'ınızı oluşturun:

### Dockerfile.jenkins
```dockerfile
FROM jenkins/jenkins:lts

USER root

# Docker CLI yükle
RUN apt-get update && \
    apt-get install -y apt-transport-https ca-certificates curl gnupg lsb-release && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg && \
    echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null && \
    apt-get update && \
    apt-get install -y docker-ce-cli docker-compose-plugin

USER jenkins
```

### Image Build ve Run
```bash
# Image oluştur
docker build -t jenkins-with-docker -f Dockerfile.jenkins .

# Container çalıştır
docker run -d \
  --name jenkins \
  -p 8181:8080 \
  -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  jenkins-with-docker
```

## Çözüm 4: Docker Socket Mount Kontrolü

Jenkins'in Docker daemon'a erişimi olduğundan emin olun:

```bash
# Jenkins container'ı çalıştırırken
docker run -d \
  --name jenkins \
  -p 8181:8080 \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v jenkins_home:/var/jenkins_home \
  jenkins/jenkins:lts

# Container içinde izinleri düzenle
docker exec -u root jenkins chmod 666 /var/run/docker.sock
```

## Test Etme

Jenkinsfile'daki Docker komutlarını test edin:

```bash
# Jenkins container'a bağlan
docker exec -it jenkins bash

# Docker komutlarını test et
docker --version
docker compose version
docker ps
docker compose -f /path/to/compose.yaml ps
```

## Webhook için Ngrok Kullanımı

```bash
# Ngrok başlat
ngrok http 8181

# GitHub Webhook URL
https://YOUR_NGROK_URL/github-webhook/
```

## Sorun Giderme

### "docker: command not found"
- Jenkins container'ına Docker CLI yüklü değil
- Docker socket mount edilmemiş
- Çözüm 3'ü kullanın

### "permission denied while trying to connect to the Docker daemon socket"
```bash
docker exec -u root jenkins chmod 666 /var/run/docker.sock
```

### "docker compose: command not found"
- Çözüm 1'deki `docker compose` (tire olmadan) kullanın
- Veya Çözüm 2 ile Docker Compose yükleyin

## Pipeline Stage Açıklamaları

Jenkinsfile'daki her stage:

1. **🚀 Checkout** - Kodu GitHub'dan çeker
2. **🐳 Docker Ayağa Kaldırma** - PostgreSQL container'ı başlatır
3. **🔧 Maven Clean** - Önceki build'i temizler
4. **📦 Maven Compile** - Kodu derler
5. **🧪 Birim Testleri** - Unit testleri çalıştırır
6. **🔗 Entegrasyon Testleri** - Integration testleri çalıştırır
7. **🌐 Selenium E2E Testleri** - Selenium testleri çalıştırır
8. **📊 Test Coverage Raporu** - JaCoCo raporu oluşturur
9. **📦 Build Package** - JAR dosyası oluşturur
10. **🐳 Docker Image Build** - Uygulama Docker image'ı oluşturur
11. **🛑 Docker Cleanup** - PostgreSQL container'ı durdurur

## İletişim

Sorun devam ederse:
1. Jenkins log'larını kontrol edin: `docker logs jenkins`
2. Jenkinsfile'daki script'leri manuel test edin
3. Docker socket izinlerini kontrol edin

