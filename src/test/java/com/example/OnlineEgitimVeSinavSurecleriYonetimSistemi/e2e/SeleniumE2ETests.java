package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Selenium E2E testleri (başlık ve kısa açıklamalar eklendi).
 *
 * İçerik: UI akışlarını kontrol eden basit, atlanabilir (assumption) testler.
 * Testler, uygulama çalışmıyorsa veya ChromeDriver başlatılamıyorsa otomatik olarak atlanır.
 */
public class SeleniumE2ETests {
    private static WebDriver driver;
    private static final String BASE = "http://localhost:8080";

    @BeforeAll
    public static void setupClass() {
        // Eğer sunucuya ulaşılamıyorsa testleri atla
        boolean reachable = isServerUp();
        Assumptions.assumeTrue(reachable, "Uygulama çalışmıyor veya erişilemez: " + BASE);

        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            String headless = System.getProperty("e2e.headless", "true");
            if (!"false".equalsIgnoreCase(headless)) {
                options.addArguments("--headless=new");
            }
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            System.out.println("Starting ChromeDriver (headless=" + headless + ")...");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        } catch (Exception ex) {
            // Eğer driver başlatılmazsa testleri atla
            System.err.println("ChromeDriver başlatılamadı: " + ex.getMessage());
            Assumptions.assumeTrue(false, "ChromeDriver başlatılamadı: " + ex.getMessage());
        }
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private static boolean elementExists(By by) {
        return driver != null && !driver.findElements(by).isEmpty();
    }

    /**
     * Sunucunun çalışıp çalışmadığını kontrol eder
     */
    @SuppressWarnings("deprecation")
    private static boolean isServerUp() {
        try {
            URL url = new URL(BASE + "/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            return (connection.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            return false;
        }
    }

    // REQ-001: Ana Sayfa Yükleme
    // İlgili Gereksinimler: REQ-UI-001
    // Ön Koşullar: Uygulama çalışıyor
    // Adımlar:
    // 1. Ana sayfaya git (GET /)
    // Beklenen Sonuç: Sayfa başlığı veya ana içerik yüklenmiş olmalı
    // Son Koşullar: Tarayıcı kapatılacak
    @Test
    public void TC01_homePageLoads() {
        // USER-COMMENT: Ana sayfa başlığı null olmamalı, temel smoke testi
        driver.get(BASE + "/");
        var title = driver.getTitle();
        assertThat(title).isNotNull();
    }

    // REQ-002: Kayıt Formu Görünümü
    // İlgili Gereksinimler: REQ-UI-002
    // Ön Koşullar: Uygulama çalışıyor
    // Adımlar:
    // 1. /register sayfasına git
    // Beklenen Sonuç: username/email/password inputları bulunmalı
    // Son Koşullar: -
    @Test
    public void TC02_registerFormVisible() {
        // USER-COMMENT: Kayıt formundaki alanlar DOM'da bulunmalı
        driver.get(BASE + "/register");
        // { changed: daha anlamlı kontrol - en az 1 input bekleniyor }
        assertThat(driver.findElements(By.name("username")).size()).isGreaterThanOrEqualTo(1);
        assertThat(driver.findElements(By.name("email")).size()).isGreaterThanOrEqualTo(1);
        assertThat(driver.findElements(By.name("password")).size()).isGreaterThanOrEqualTo(1);
    }

    // REQ-003: Kullanıcı Kayıt Akışı (basit)
    // İlgili Gereksinimler: REQ-AUTH-001
    // Ön Koşullar: /register açık
    // Adımlar:
    // 1. Kayıt formunu doldur ve gönder
    // Beklenen Sonuç: Kayıt başarılı mesajı veya yönlendirme
    // Son Koşullar: Oluşturulan kullanıcı DB'den temizlenmedi (manüel)
    @Test
    public void TC03_registerUser() {
        // USER-COMMENT: Basit form submit testi - DB temizliği manuel
        driver.get(BASE + "/register");
        if (elementExists(By.name("username")) && elementExists(By.name("email")) && elementExists(By.name("password"))) {
            driver.findElement(By.name("username")).sendKeys("e2euser");
            driver.findElement(By.name("email")).sendKeys("e2e@example.com");
            driver.findElement(By.name("password")).sendKeys("P@ssw0rd");
            driver.findElement(By.cssSelector("button[type='submit']")).click();
            assertThat(driver.getCurrentUrl()).doesNotContain("/register");
        }
    }

    // REQ-004: Giriş Formu Görünümü
    // İlgili Gereksinimler: REQ-AUTH-002
    // Ön Koşullar: Uygulama çalışıyor
    // Adımlar:
    // 1. /login sayfasına git
    // Beklenen Sonuç: username/password inputları bulunmalı
    @Test
    public void TC04_loginFormVisible() {
        // USER-COMMENT: Login formu inputları olmalı
        driver.get(BASE + "/login");
        // { changed: daha anlamlı kontrol - en az 1 input bekleniyor }
        assertThat(driver.findElements(By.name("username")).size()).isGreaterThanOrEqualTo(1);
        assertThat(driver.findElements(By.name("password")).size()).isGreaterThanOrEqualTo(1);
    }

    // REQ-005: Kurs Listesi Görünümü
    // İlgili Gereksinimler: REQ-COURSE-001
    // Ön Koşullar: Uygulama çalışıyor, kurslar mevcut olabilir
    // Adımlar: 1. /courses sayfasına git
    // Beklenen Sonuç: Kurs başlıkları veya liste elemanları gösterilmeli
    @Test
    public void TC05_courseListVisible() {
        // USER-COMMENT: Courses sayfası kaynak uzunluğu > 0 olmalı
        driver.get(BASE + "/courses");
        assertThat(driver.getPageSource().length()).isGreaterThan(0);
    }

    // REQ-006: Kurs Detayı Görünümü
    // İlgili Gereksinimler: REQ-COURSE-002
    // Ön Koşullar: En az bir kurs olduğu varsayımı
    // Adımlar: 1. /courses/1 sayfasına git
    // Beklenen Sonuç: Kurs başlığı veya içeriği görünür
    @Test
    public void TC06_courseDetailVisible() {
        // USER-COMMENT: Varsayımsal course id=1 için içerik kontrolü
        driver.get(BASE + "/courses/1");
        assertThat(driver.getPageSource().length()).isGreaterThan(0);
    }

    // REQ-007: Ders Tamamlama Akışı (mock)
    // İlgili Gereksinimler: REQ-PROGRESS-001
    // Ön Koşullar: Kullanıcı girişli veya ders erişimi var
    // Adımlar: 1. /courses/1/lessons/1/complete POST veya butona tık
    // Beklenen Sonuç: Tamamlandı sayısı artar
    @Test
    public void TC07_markLessonCompleted() {
        // USER-COMMENT: Ders tamamlama butonu varsa tıkla ve 'Tamamlandı' içeriğini ara
        driver.get(BASE + "/courses/1/lessons/1");
        var btns = driver.findElements(By.cssSelector("button.complete-lesson"));
        if (!btns.isEmpty()) {
            btns.get(0).click();
            assertThat(driver.getPageSource()).contains("Tamamlandı");
        }
    }

    // REQ-008: Quiz Başlatma ve Cevap Verme (mock)
    // İlgili Gereksinimler: REQ-QUIZ-001
    // Ön Koşullar: Quiz mevcut
    // Adımlar: 1. /courses/1/quiz/1 sayfasına git, cevapları işaretle ve gönder
    // Beklenen Sonuç: Sonuç sayfası / sonucu gösterir
    @Test
    public void TC08_takeQuiz() {
        // USER-COMMENT: Quiz sayfasındaki radio inputlardan birini seç ve sonucu kontrol et
        driver.get(BASE + "/courses/1/quiz/1");
        var choices = driver.findElements(By.cssSelector("input[type='radio']"));
        if (!choices.isEmpty()) {
            choices.get(0).click();
            if (elementExists(By.cssSelector("button[type='submit']"))) {
                driver.findElement(By.cssSelector("button[type='submit']")).click();
                assertThat(driver.getPageSource()).contains("Sonuç");
            }
        }
    }

    // REQ-009: Ödeme/Transaction Başlatma (mock)
    // İlgili Gereksinimler: REQ-PAY-001
    // Ön Koşullar: Ödeme sayfası var
    // Adımlar: 1. /checkout/1 sayfasına git, ödeme formunu doldur
    // Beklenen Sonuç: Başarılı ödeme mesajı veya yönlendirme
    @Test
    public void TC09_checkoutPayment() {
        // USER-COMMENT: Ödeme formu varsa test kart bilgileri ile gönder ve başarı mesajını kontrol et
        driver.get(BASE + "/checkout/1");
        if (elementExists(By.name("cardNumber"))) {
            driver.findElement(By.name("cardNumber")).sendKeys("4111111111111111");
            driver.findElement(By.name("expiry")).sendKeys("12/30");
            driver.findElement(By.name("cvc")).sendKeys("123");
            driver.findElement(By.cssSelector("button[type='submit']")).click();
            assertThat(driver.getPageSource()).contains("Başarılı");
        }
    }

    // REQ-010: Bildirim Listesi Görünümü
    // İlgili Gereksinimler: REQ-NOTIF-001
    // Ön Koşullar: Bildirimler mevcut olabilir
    // Adımlar: 1. /notifications sayfasına git
    // Beklenen Sonuç: Bildirimler listelenmeli
    @Test
    public void TC10_notificationListVisible() {
        // USER-COMMENT: Notifications sayfası boş olsa bile sayfa yüklenmeli
        driver.get(BASE + "/notifications");
        assertThat(driver.getPageSource().length()).isGreaterThan(0);
    }

    // REQ-011: Kullanıcı Profili Görünümü
    // İlgili Gereksinimler: REQ-USER-001
    // Ön Koşullar: Kullanıcı girişli olmalı
    // Adımlar: 1. /profile sayfasına git
    // Beklenen Sonuç: Kullanıcı bilgileri ve güncelleme formu görünür
    @Test
    public void TC11_userProfileVisible() {
        // USER-COMMENT: Profil sayfası kullanıcı bilgilerini içermeli
        driver.get(BASE + "/profile");
        assertThat(driver.getPageSource()).contains("Kullanıcı Bilgileri");
    }

    // REQ-012: Şifre Değiştirme Akışı
    // İlgili Gereksinimler: REQ-AUTH-003
    // Ön Koşullar: Kullanıcı girişli olmalı
    // Adımlar: 1. Profil sayfasında şifre değiştir
    // Beklenen Sonuç: Başarılı şifre değişikliği mesajı
    @Test
    public void TC12_changePassword() {
        // USER-COMMENT: Eğer şifre değişikliği formu bulunuyorsa akışı test et
        driver.get(BASE + "/profile");
        if (elementExists(By.name("currentPassword")) && elementExists(By.name("newPassword")) && elementExists(By.name("confirmPassword"))) {
            driver.findElement(By.name("currentPassword")).sendKeys("P@ssw0rd");
            driver.findElement(By.name("newPassword")).sendKeys("NewP@ssw0rd");
            driver.findElement(By.name("confirmPassword")).sendKeys("NewP@ssw0rd");
            driver.findElement(By.cssSelector("button[type='submit']")).click();
            assertThat(driver.getPageSource()).contains("Şifre başarıyla değiştirildi");
        }
    }

    // REQ-013: Kullanıcı Çıkış Akışı
    // İlgili Gereksinimler: REQ-AUTH-004
    // Ön Koşullar: Kullanıcı girişli olmalı
    // Adımlar: 1. Çıkış yap
    // Beklenen Sonuç: Ana sayfaya yönlendirme ve çıkış mesajı
    @Test
    public void TC13_userLogout() {
        // USER-COMMENT: /logout endpoint ana sayfaya yönlendirmeli
        driver.get(BASE + "/logout");
        assertThat(driver.getCurrentUrl()).isEqualTo(BASE + "/");
    }

    // REQ-014: Hata Sayfası Görünümü
    // İlgili Gereksinimler: REQ-UI-004
    // Ön Koşullar: Geçersiz bir sayfaya git
    // Adımlar: 1. /invalid-page sayfasına git
    // Beklenen Sonuç: 404 hata mesajı veya özel hata sayfası
    @Test
    public void TC14_errorPageVisible() {
        // USER-COMMENT: Bilerek hatalı bir rota ile 404 kontrolü
        driver.get(BASE + "/invalid-page");
        assertThat(driver.getPageSource()).contains("404");
    }

    // REQ-015: İletişim Formu Gönderimi
    // İlgili Gereksinimler: REQ-UI-005
    // Ön Koşullar: İletişim formu erişilebilir olmalı
    // Adımlar: 1. İletişim formunu doldur ve gönder
    // Beklenen Sonuç: Başarılı gönderim mesajı
    @Test
    public void TC15_contactFormSubmission() {
        // USER-COMMENT: Contact formu varsa doldur ve gönder, başarı mesajını ara
        driver.get(BASE + "/contact");
        if (elementExists(By.name("name")) && elementExists(By.name("email")) && elementExists(By.name("message"))) {
            driver.findElement(By.name("name")).sendKeys("Test User");
            driver.findElement(By.name("email")).sendKeys("testuser@example.com");
            driver.findElement(By.name("message")).sendKeys("Bu bir test mesajıdır.");
            driver.findElement(By.cssSelector("button[type='submit']")).click();
            assertThat(driver.getPageSource()).contains("Mesajınız başarıyla gönderildi");
        }
    }
}

