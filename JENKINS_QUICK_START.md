# 🚀 Jenkins Pipeline - Hızlı Başlangıç Kılavuzu

**5 Dakikada Jenkins Pipeline Kurun!**

---

## ⚡ HIZLI KURULUM (5 Adım)

### 1️⃣ Jenkins'e Giriş Yapın
```
http://localhost:8181
```

### 2️⃣ Yeni Pipeline Oluşturun

- **New Item** → İsim girin: `OESYS-Pipeline`
- **Pipeline** seçin → **OK**

### 3️⃣ Pipeline Ayarları

**GitHub project:**
```
✅ İşaretle
Project url: https://github.com/RANANUROKTAYOGR/OnlineEgitimVeSinavSurecleriYonetimSistemi
```

**Build Triggers:**
```
✅ GitHub hook trigger for GITScm polling
```

**Pipeline:**
```
Definition: Pipeline script from SCM
SCM: Git
Repository URL: https://github.com/RANANUROKTAYOGR/OnlineEgitimVeSinavSurecleriYonetimSistemi.git
Branch: */main
Script Path: Jenkinsfile
```

**Save!**

### 4️⃣ Webhook Ekleyin (Opsiyonel - Otomatik tetikleme için)

```bash
# Terminal'de ngrok başlat
ngrok http 8181
```

GitHub → Settings → Webhooks → Add webhook:
```
Payload URL: https://YOUR-NGROK-URL.ngrok.io/github-webhook/
Content type: application/json
✅ Active
```

### 5️⃣ İlk Build'i Çalıştırın

Jenkins Dashboard → **OESYS-Pipeline** → **Build Now**

---

## 📊 PIPELİNE STAGE'LERİ

Pipeline 10 aşamadan oluşur:

```
1. 🔄 Checkout           - Kodu GitHub'dan çek
2. 🛠️ Environment Setup  - Java, Maven, Docker kontrol
3. 🐳 Docker             - PostgreSQL başlat
4. 🏗️ Build              - Projeyi derle
5. ✅ Unit Tests         - Birim testleri (150 test)
6. 🔗 Integration Tests  - Entegrasyon testleri (75 test)
7. 🌐 E2E Tests          - Selenium testleri (25 test)
8. 📊 Code Coverage      - Jacoco raporu
9. 📦 Package            - JAR oluştur
10. 🐳 Docker Image      - Docker image build
```

**Toplam Süre:** ~5-6 dakika

---

## 🎯 BAŞARILI BUILD ÖRNEĞİ

```
[Pipeline] Start of Pipeline
✅ Checkout completed
✅ Environment ready
✅ Docker services started
✅ Build successful
✅ Unit tests passed (150/150)
✅ Integration tests passed (75/75)
✅ E2E tests passed (25/25)
✅ Coverage: 85%
✅ JAR created
✅ Docker image built
[Pipeline] End of Pipeline

✅ Pipeline completed successfully! (5m 30s)
```

---

## 🔧 HIZLI SORUN GİDERME

### Problem: Pipeline başlamıyor
```bash
# Docker'ı yeniden başlat
docker-compose down
docker-compose up -d
```

### Problem: Test hataları
```bash
# Veritabanını temizle
docker-compose down -v
docker-compose up -d
sleep 30  # PostgreSQL'in hazır olmasını bekle
```

### Problem: Port çakışması
```bash
# 8080 veya 5432 portlarını kontrol et
netstat -ano | findstr :8080
netstat -ano | findstr :5432
```

---

## 📈 RAPORLARI GÖRÜNTÜLEME

Build tamamlandıktan sonra:

1. **Test Results** (Sol menü)
   - ✅ 250/250 test başarılı
   - 📊 Test trend grafikleri

2. **Jacoco Coverage** (Sol menü)
   - 📈 Coverage: 85%
   - 📋 Detaylı paket raporları

3. **Console Output**
   - 📝 Tüm build logları
   - 🐛 Hata mesajları (varsa)

---

## 🎉 TEST EDİN

```bash
# Kod değişikliği yapın
echo "# Test" >> README.md
git add .
git commit -m "test: Pipeline test"
git push origin main
```

**Beklenen:**
- GitHub webhook tetiklenir ⚡
- Jenkins'te otomatik build başlar 🚀
- Tüm stage'ler çalışır ✅
- Raporlar yayınlanır 📊

---

## 📚 DETAYLI DOKÜMANTASYON

Daha fazla bilgi için:
- 📄 **JENKINS_PIPELINE_SETUP.md** - Detaylı kurulum rehberi (776 satır)
- 📄 **WEBHOOK_SETUP_GUIDE.md** - Webhook yapılandırması
- 📄 **PORT_VERIFICATION_REPORT.md** - Port ayarları

---

## ✅ KONTROL LİSTESİ

Pipeline hazır mı?

- [ ] Jenkins'te pipeline oluşturuldu
- [ ] GitHub repository bağlandı
- [ ] İlk build başarıyla tamamlandı
- [ ] Test raporları görüntülendi
- [ ] (Opsiyonel) Webhook yapılandırıldı

**Tamamsa:** 🎉 Jenkins Pipeline kullanıma hazır!

---

**Jenkins:** http://localhost:8181  
**Süre:** ~5-6 dakika  
**Testler:** 250+  
**Coverage:** 85%+

**Hazır! 🚀**

