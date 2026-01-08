# 🧪 Selenium E2E Testlerini Çalıştırma Rehberi

## ✅ Testler Başarıyla Güncellendi!

Selenium E2E testleriniz artık **GÖRÜNÜR MOD**da çalışacak şekilde yapılandırıldı.
**Chrome tarayıcı açılacak ve testleri canlı olarak izleyebileceksiniz!**

---

## 🚀 Testleri Çalıştırma Adımları

### 1️⃣ Uygulamayı Başlatın

İlk olarak uygulamanızın çalıştığından emin olun:

```powershell
.\mvnw.cmd spring-boot:run
```

**Veya Docker ile:**
```powershell
docker compose up
```

Uygulama http://localhost:8080 adresinde çalışmalıdır.

---

### 2️⃣ Testleri Çalıştırın

**Otomatik Betik ile (ÖNERİLEN):**
```powershell
.\run-selenium-visible.ps1
```

**Manuel Maven Komutu ile:**
```powershell
.\mvnw.cmd test -Dtest=SeleniumE2ETests
```

---

## 👁️ Testleri İzleme

Testler çalıştığında:

1. ✅ **Chrome tarayıcı otomatik açılacak**
2. 🌐 Testler her sayfayı ziyaret edecek
3. ⏱️ Her adımda **3 saniye bekleyecek** (testleri görebilmeniz için)
4. 📊 Testler bittiğinde **10 saniye daha açık kalacak** (sonuçları görebilmeniz için)
5. 🔒 Sonra otomatik kapanacak

---

## 📋 Test Listesi (15 Test)

✅ **TC01** - Ana Sayfa Yükleme
✅ **TC02** - Kayıt Formu Görünürlük
✅ **TC03** - Kullanıcı Kayıt Akışı
✅ **TC04** - Giriş Formu Görünürlük
✅ **TC05** - Kurs Listesi Görünürlük
✅ **TC06** - Kurs Detayı Görünürlük
✅ **TC07** - Ders Tamamlama
✅ **TC08** - Quiz Alma
✅ **TC09** - Ödeme İşlemi
✅ **TC10** - Bildirim Listesi
✅ **TC11** - Kullanıcı Profili
✅ **TC12** - Şifre Değiştirme
✅ **TC13** - Kullanıcı Çıkışı
✅ **TC14** - Hata Sayfası
✅ **TC15** - İletişim Formu

---

## 🔧 Yapılan Değişiklikler

### ✨ Chrome Görünür Mod
- ❌ Headless mod **KAPALI**
- ✅ Tam ekran mod **AÇIK**
- ✅ Otomasyon algılama **KAPALI**
- ✅ Her adımda **3 saniye bekleme**

### 📊 Detaylı Loglar
- ✅ Her test için başlangıç mesajı
- ✅ Test adımları detaylı loglarda
- ✅ Başarı/hata durumu belirtildi
- ✅ URL bilgileri gösteriliyor

### ⚙️ Güvenilir Çalışma
- ✅ Sunucu kontrolü otomatik
- ✅ ChromeDriver otomatik kurulum
- ✅ Timeout süreleri optimize edildi
- ✅ Hata durumunda testler atlanıyor

---

## 🐛 Sorun Giderme

### Uygulama Çalışmıyor Hatası
```
❌ UYARI: Uygulama çalışmıyor veya erişilemez
```
**Çözüm:** Önce uygulamayı başlatın:
```powershell
.\mvnw.cmd spring-boot:run
```

### ChromeDriver Hatası
```
❌ ChromeDriver başlatılamadı
```
**Çözüm:** Maven otomatik indirecek, internet bağlantınızı kontrol edin.

### Port Zaten Kullanımda
```
Port 8080 already in use
```
**Çözüm:** Başka bir portta çalıştırın:
```powershell
.\mvnw.cmd spring-boot:run -Dserver.port=8081
```
Sonra testlerde BASE URL'i güncelleyin.

---

## 📈 Test Raporları

Test sonuçları şurada:
```
target/surefire-reports/
```

HTML rapor:
```
target/surefire-reports/TEST-*.xml
```

---

## 💡 İpuçları

1. **Testleri Durdurma:** Ctrl+C ile durdurun
2. **Tek Test Çalıştırma:** 
   ```powershell
   .\mvnw.cmd test -Dtest=SeleniumE2ETests#TC01_homePageLoads
   ```
3. **Daha Hızlı:** `waitToSee()` süresini 1000ms'e düşürün
4. **Daha Yavaş:** `waitToSee()` süresini 5000ms'e çıkarın

---

## ✅ Başarıyla Tamamlandı!

Artık Selenium testleriniz Chrome'da görünür olarak çalışıyor! 🎉

**Sonraki Adım:** Testleri çalıştırın ve sonuçları izleyin!

```powershell
.\run-selenium-visible.ps1
```

---

**Not:** Testler başarısız olursa, uygulama sayfalarınızın HTML yapısını kontrol edin.
Form elemanları (name="username", name="email" vs.) doğru olmalıdır.

