# 📚 Online Eğitim ve Sınav Süreçleri Yönetim Sistemi (OESYS)

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![Maven](https://img.shields.io/badge/Maven-3.9.12-red)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 🎯 Proje Hakkında

OESYS (Online Eğitim ve Sınav Süreçleri Yönetim Sistemi), kapsamlı bir e-öğrenme platformudur. Kurs yönetimi, öğrenci takibi, sınav sistemi, ödeme yönetimi ve raporlama özelliklerini içerir.

## ✨ Özellikler

### 📖 Eğitim Yönetimi
- ✅ Kurs oluşturma ve yönetimi
- ✅ Modül ve ders organizasyonu
- ✅ Video, doküman ve interaktif içerik desteği
- ✅ İlerleme takibi ve raporlama

### 📝 Sınav Sistemi
- ✅ Quiz oluşturma ve yönetimi
- ✅ Çoktan seçmeli sorular
- ✅ Otomatik puanlama sistemi
- ✅ Detaylı sonuç analizi

### 👥 Kullanıcı Yönetimi
- ✅ Öğrenci, eğitmen ve admin rolleri
- ✅ Güvenli kimlik doğrulama (Spring Security)
- ✅ Profil yönetimi
- ✅ Rol bazlı erişim kontrolü

### 💰 Ödeme ve Komisyon
- ✅ Kurs satın alma işlemleri
- ✅ Transaction yönetimi
- ✅ Eğitmen komisyon hesaplama
- ✅ Ödeme geçmişi takibi

### 🎓 Sertifika Sistemi
- ✅ Otomatik sertifika oluşturma
- ✅ Kurs tamamlama kriterleri
- ✅ PDF sertifika indirme

### 🔔 Bildirim Sistemi
- ✅ Otomatik e-posta bildirimleri
- ✅ Sistem içi mesajlaşma
- ✅ Önemli etkinlik bildirimleri

## 🏗️ Teknoloji Stack

### Backend
- **Java 21** - Modern Java özellikleri
- **Spring Boot 4.0.1** - Enterprise framework
- **Spring Data JPA** - ORM ve veritabanı erişimi
- **Spring Security** - Güvenlik ve kimlik doğrulama
- **Hibernate 7.2.0** - ORM implementasyonu
- **PostgreSQL 15** - İlişkisel veritabanı
- **Lombok** - Boilerplate kod azaltma

### Testing
- **JUnit 5** - Birim testleri
- **Mockito** - Mock framework
- **Testcontainers** - Entegrasyon testleri
- **Selenium** - E2E testleri
- **JaCoCo** - Kod coverage analizi

### DevOps & Tools
- **Maven** - Build ve dependency yönetimi
- **Docker** - Containerization
- **Jenkins** - CI/CD pipeline
- **Ngrok** - Local development webhook

## 📊 Veritabanı Şeması

### Ana Tablolar
1. **Users** - Kullanıcı bilgileri
2. **UserRoles** - Kullanıcı rolleri
3. **Courses** - Kurs bilgileri
4. **Modules** - Kurs modülleri
5. **Lessons** - Dersler
6. **Quizzes** - Sınavlar
7. **QuizQuestions** - Sınav soruları
8. **Enrollments** - Kayıtlar
9. **Submissions** - Sınav cevapları
10. **Transactions** - Ödeme işlemleri
11. **Certificates** - Sertifikalar
12. **Notifications** - Bildirimler

## 🚀 Kurulum

### Ön Gereksinimler
- Java 21
- PostgreSQL 15
- Maven 3.9+
- Docker (opsiyonel)

### 1. Repository'yi Klonlayın
```bash
git clone https://github.com/YOUR-USERNAME/OnlineEgitimVeSinavSurecleriYonetimSistemi.git
cd OnlineEgitimVeSinavSurecleriYonetimSistemi
```

### 2. PostgreSQL Veritabanını Oluşturun
```sql
CREATE DATABASE oesys_db;
```

### 3. Environment Variables Ayarlayın
```bash
# Windows
set DB_PASSWORD=your_password

# Linux/Mac
export DB_PASSWORD=your_password
```

### 4. Uygulamayı Çalıştırın
```bash
# Maven ile
./mvnw spring-boot:run

# Veya JAR ile
./mvnw clean package -DskipTests
java -jar target/OnlineEgitimVeSinavSurecleriYonetimSistemi-0.0.1-SNAPSHOT.jar
```

Uygulama `http://localhost:8080` adresinde çalışacaktır.

## 🧪 Test Çalıştırma

### Tüm Testler
```bash
./mvnw clean test
```

### Sadece Birim Testleri
```bash
./mvnw test -Dtest=*Test
```

### Entegrasyon Testleri
```bash
./mvnw verify -P integration-test
```

### E2E Testleri (Selenium)
```bash
./mvnw verify -P e2e-test
```

### Coverage Raporu
```bash
./mvnw clean test jacoco:report
# Rapor: target/site/jacoco/index.html
```

## 📈 Test Kapsamı

- **Birim Testleri:** 25+ test
- **Entegrasyon Testleri:** 15+ test
- **E2E Testleri:** 15+ Selenium test
- **Toplam Test Sayısı:** 55+
- **Code Coverage:** %80+

## 🔧 Konfigürasyon

### application.properties
```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/oesys_db
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD:postgres}

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Hikari Pool
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=2
```

### Docker ile Çalıştırma
```bash
# Docker Compose ile PostgreSQL
docker-compose up -d

# Uygulamayı Docker'da çalıştır
docker build -t oesys .
docker run -p 8080:8080 -e DB_PASSWORD=your_password oesys
```

## 📋 API Endpoints (Planlanan)

### Authentication
- `POST /api/auth/login` - Kullanıcı girişi
- `POST /api/auth/register` - Yeni kullanıcı kaydı
- `POST /api/auth/logout` - Çıkış

### Courses
- `GET /api/courses` - Tüm kursları listele
- `GET /api/courses/{id}` - Kurs detayı
- `POST /api/courses` - Yeni kurs oluştur
- `PUT /api/courses/{id}` - Kurs güncelle
- `DELETE /api/courses/{id}` - Kurs sil

### Enrollments
- `POST /api/enrollments` - Kursa kayıt ol
- `GET /api/enrollments/my-courses` - Kayıtlı kurslarım

### Quizzes
- `GET /api/quizzes/{id}` - Quiz detayı
- `POST /api/submissions` - Quiz cevapları gönder
- `GET /api/submissions/{id}/result` - Quiz sonucu

## 🔄 CI/CD Pipeline

Jenkins pipeline'ı otomatik olarak:
1. ✅ Git checkout
2. ✅ Docker PostgreSQL başlat
3. ✅ Derleme
4. ✅ Birim testleri
5. ✅ Entegrasyon testleri
6. ✅ Uygulama başlat
7. ✅ Selenium E2E testleri
8. ✅ Test raporları
9. ✅ Build
10. ✅ Cleanup

## 🤝 Katkıda Bulunma

1. Bu repository'yi fork edin
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Değişikliklerinizi commit edin (`git commit -m 'Add some amazing feature'`)
4. Branch'inizi push edin (`git push origin feature/amazing-feature`)
5. Pull Request açın

## 📝 Lisans

Bu proje MIT lisansı altında lisanslanmıştır.

## 👨‍💻 Geliştirici

**Rana Nur Oktay**

## 📧 İletişim

Proje hakkında sorularınız için issue açabilirsiniz.

## 🙏 Teşekkürler

Spring Boot, PostgreSQL ve tüm açık kaynak topluluğuna teşekkürler!

---

⭐ Projeyi beğendiyseniz yıldız vermeyi unutmayın!

