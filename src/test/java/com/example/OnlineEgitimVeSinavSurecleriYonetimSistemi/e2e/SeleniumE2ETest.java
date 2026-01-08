package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("duplicate - use SeleniumE2ETests")
public class SeleniumE2ETest {

    private boolean isAppUp() {
        try {
            URL url = new URL("http://localhost:8080/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(2000);
            con.setReadTimeout(2000);
            con.setRequestMethod("GET");
            int code = con.getResponseCode();
            return code >= 200 && code < 500; // reachable
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    void homepageLoads_and_titlePresent() {
        Assumptions.assumeTrue(isAppUp(), "Skipping E2E: app not reachable at http://localhost:8080");

        try {
            WebDriverManager.chromedriver().setup();
        } catch (Exception e) {
            Assumptions.assumeTrue(false, "Skipping E2E: cannot setup ChromeDriver: " + e.getMessage());
            return;
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = null;
        try {
            driver = new ChromeDriver(options);
        } catch (Exception e) {
            Assumptions.assumeTrue(false, "Skipping E2E: cannot start ChromeDriver session: " + e.getMessage());
        }

        try {
            driver.get("http://localhost:8080/");
            String title = driver.getTitle();
            assertThat(title).isNotNull();

            boolean hasHeader = false;
            try {
                WebElement h = driver.findElement(By.tagName("h1"));
                hasHeader = h.getText() != null && !h.getText().isEmpty();
            } catch (Exception ignored) {
            }

            assertThat(hasHeader || driver.getPageSource().contains("Online") || driver.getPageSource().contains("OESYS")).isTrue();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
