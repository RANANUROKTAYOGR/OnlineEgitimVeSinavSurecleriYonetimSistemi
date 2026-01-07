# Jenkins Docker Compose Hatası - Hızlı Çözüm

## ❌ Hata
```
docker-compose: not found
```

## ✅ Çözüm (UYGULANMIŞ)

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

```bash
# Jenkins container'a bağlan
docker exec -it jenkins bash

# Docker komutlarını test et
docker --version
docker compose version
docker ps
```

## ✅ Push Durumu

- ✅ Jenkinsfile güncellendi
- ✅ GitHub'a push edildi
- ✅ Webhook tetiklenecek
- ✅ Pipeline yeniden çalışacak

## 🚀 Sonraki Adım

Jenkins'te yeni build'in başlamasını bekleyin. Docker komutları artık çalışmalı!

