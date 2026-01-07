# 🌐 Jenkins Webhook + Ngrok Yapılandırma Rehberi

## 📋 Gereksinimler

- ✅ Yerel Jenkins kurulu olmalı (http://localhost:8181)
- ✅ Ngrok hesabı ve kurulu yazılım
- ✅ GitHub repository'ye admin erişimi

---

## 🚀 Adım 1: Ngrok Kurulumu

### Windows için Ngrok İndirme:

```bash
# Ngrok indirin: https://ngrok.com/download
# Veya Chocolatey ile:
choco install ngrok
```

### Ngrok Kimlik Doğrulama:

```bash
# Ngrok hesabınızdan auth token alın
ngrok config add-authtoken YOUR_AUTH_TOKEN
```

---

## 🔗 Adım 2: Jenkins için Ngrok Tüneli Açma

### Jenkins için Tünel Başlatın:

```bash
ngrok http 8181
```

**Çıktı örneği:**
```
Forwarding  https://abc123.ngrok.io -> http://localhost:8181
```

⚠️ **ÖNEMLİ:** `https://abc123.ngrok.io` URL'ini kopyalayın!

---

## 🔧 Adım 3: Jenkins Webhook Yapılandırması

### 3.1 Jenkins'te Proje Oluşturma

1. Jenkins Dashboard'a gidin: `http://localhost:8181`
2. **New Item** → **Pipeline** seçin
3. Proje adı: `OESYS-Pipeline`
4. **OK** tıklayın

### 3.2 Pipeline Yapılandırması

**Build Triggers bölümünde:**
- ✅ **GitHub hook trigger for GITScm polling** seçeneğini işaretleyin

**Pipeline bölümünde:**
- **Definition:** `Pipeline script from SCM`
- **SCM:** `Git`
- **Repository URL:** 
  ```
  https://github.com/RANANUROKTAYOGR/OnlineEgitimVeSinavSurecleriYonetimSistemi.git
  ```
- **Branch:** `*/main`
- **Script Path:** `Jenkinsfile`

**Save** tıklayın.

---

## 🔗 Adım 4: GitHub Webhook Ekleme

### 4.1 GitHub Repository Settings

1. Repository'ye gidin: https://github.com/RANANUROKTAYOGR/OnlineEgitimVeSinavSurecleriYonetimSistemi
2. **Settings** → **Webhooks** → **Add webhook**

### 4.2 Webhook Yapılandırması

- **Payload URL:** 
  ```
  https://YOUR-NGROK-URL.ngrok.io/github-webhook/
  ```
  Örnek: `https://abc123.ngrok.io/github-webhook/`

- **Content type:** `application/json`

- **Which events would you like to trigger this webhook?**
  - ✅ `Just the push event`
  - Veya **Let me select individual events:**
    - ✅ Pushes
    - ✅ Pull requests

- **Active:** ✅ İşaretli olmalı

**Add webhook** tıklayın.

---

## ✅ Adım 5: Webhook Test Etme

### 5.1 Test Push:

```bash
cd "C:\Users\RANA NUR OKTAY\Desktop\Intellij IDEA\OnlineEgitimVeSinavSurecleriYonetimSistemi"

# Küçük bir değişiklik yapın
echo "# Webhook test" >> README.md

# Commit ve push
git add README.md
git commit -m "test: Webhook test commit"
git push origin main
```

### 5.2 Jenkins'te Kontrol:

1. Jenkins Dashboard: `http://localhost:8181`
2. **OESYS-Pipeline** projesine gidin
3. Yeni bir build otomatik başlamalı!

### 5.3 GitHub'da Kontrol:

1. Repository → **Settings** → **Webhooks**
2. Webhook'a tıklayın
3. **Recent Deliveries** sekmesinde:
   - ✅ Yeşil tik: Başarılı
   - ❌ Kırmızı X: Hata var

---

## 🎯 Pipeline Stages Açıklaması

Jenkins pipeline şu aşamaları içerir:

1. **🚀 Checkout** - Kodu GitHub'dan çeker
2. **🐳 Docker Ayağa Kaldırma** - PostgreSQL container'ı başlatır
3. **🔧 Maven Clean** - Önceki build'leri temizler
4. **📦 Maven Compile** - Kodu derler
5. **🧪 Birim Testleri** - Unit testleri çalıştırır
6. **🔗 Entegrasyon Testleri** - Integration testleri çalıştırır
7. **🌐 Selenium E2E Testleri** - Selenium testlerini çalıştırır
8. **📊 Test Coverage Raporu** - JaCoCo raporu oluşturur
9. **📦 Build Package** - JAR dosyası oluşturur
10. **🐳 Docker Image Build** - Docker image oluşturur
11. **🛑 Docker Cleanup** - Container'ları durdurur

---

## 📊 Test Raporlarını Görüntüleme

### Jenkins Dashboard'da:

1. Build'e tıklayın
2. **JaCoCo Coverage Report** - Code coverage
3. **Unit Test Report** - Test sonuçları
4. **Console Output** - Detaylı loglar

---

## 🔧 Sorun Giderme

### Webhook 404 Hatası:

```bash
# Jenkins Generic Webhook Trigger plugini kurulumu
Jenkins → Manage Jenkins → Plugins → Available
# "Generic Webhook Trigger Plugin" ara ve kur
```

### Ngrok Bağlantı Hatası:

```bash
# Ngrok'u yeniden başlat
ngrok http 8181
# Yeni URL'yi GitHub webhook'a güncelle
```

### Jenkins Build Başlamıyor:

1. Jenkins → **Manage Jenkins** → **Configure System**
2. **GitHub Server** bölümünde:
   - ✅ **Manage hooks** işaretli olmalı

---

## 🎉 Webhook Başarı Kontrolü

✅ **Webhook çalışıyor mu?**

1. GitHub'a kod push edin
2. Jenkins'te otomatik build başlamalı
3. GitHub Webhook deliveries yeşil tik göstermeli
4. Jenkins Console Output webhook tetiklemesini göstermeli

---

## 🔐 Güvenlik Notları

⚠️ **Ngrok Free Plan Limitasyonları:**
- URL her yeniden başlatmada değişir
- 40 connection/dakika limit
- Kalıcı URL için ücretli plan gerekli

💡 **Production için:**
- Ngrok yerine gerçek domain kullanın
- Jenkins'i public sunucuda çalıştırın
- SSL sertifikası ekleyin
- Jenkins güvenlik ayarlarını yapılandırın

---

## 📞 Yardım

**Webhook çalışmazsa:**
1. Ngrok tüneli açık mı kontrol edin
2. Jenkins çalışıyor mu kontrol edin
3. GitHub webhook URL'sinin doğru olduğunu kontrol edin
4. Jenkins Console Output'u inceleyin
5. GitHub Webhook Recent Deliveries'i kontrol edin

---

**Proje:** OESYS - Online Eğitim ve Sınav Süreçleri Yönetim Sistemi
**Geliştirici:** Rana Nur OKTAY
**Tarih:** 2026-01-07

