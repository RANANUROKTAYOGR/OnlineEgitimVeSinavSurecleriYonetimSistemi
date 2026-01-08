# Selenium E2E Test Runner
# Bu script önce uygulamayı başlatır, ardından Selenium testlerini çalıştırır

Write-Host "================================" -ForegroundColor Cyan
Write-Host "Selenium E2E Test Runner" -ForegroundColor Cyan
Write-Host "================================" -ForegroundColor Cyan
Write-Host ""

# Uygulama çalışıyor mu kontrol et
Write-Host "1. Uygulamanın çalışıp çalışmadığı kontrol ediliyor..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080" -Method Head -TimeoutSec 5 -ErrorAction Stop
    Write-Host "   ✓ Uygulama zaten çalışıyor!" -ForegroundColor Green
    $appRunning = $true
} catch {
    Write-Host "   ✗ Uygulama çalışmıyor" -ForegroundColor Red
    $appRunning = $false
}

# Eğer uygulama çalışmıyorsa başlat
if (-not $appRunning) {
    Write-Host ""
    Write-Host "2. Uygulama başlatılıyor..." -ForegroundColor Yellow
    Write-Host "   (Yeni bir PowerShell penceresinde açılacak)" -ForegroundColor Gray

    $appProcess = Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$PSScriptRoot'; .\mvnw.cmd spring-boot:run" -PassThru

    Write-Host "   Uygulamanın başlaması bekleniyor (45 saniye)..." -ForegroundColor Gray
    Start-Sleep -Seconds 45

    # Tekrar kontrol et
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8080" -Method Head -TimeoutSec 5 -ErrorAction Stop
        Write-Host "   ✓ Uygulama başarıyla başlatıldı!" -ForegroundColor Green
    } catch {
        Write-Host "   ✗ Uygulama başlatılamadı!" -ForegroundColor Red
        Write-Host "   Lütfen manuel olarak başlatın: .\mvnw.cmd spring-boot:run" -ForegroundColor Yellow
        exit 1
    }
}

# Selenium testlerini çalıştır
Write-Host ""
Write-Host "3. Selenium E2E testleri çalıştırılıyor..." -ForegroundColor Yellow
Write-Host "   Chrome tarayıcısı açılacak ve testler visible mode'da çalışacak" -ForegroundColor Gray
Write-Host ""

.\mvnw.cmd test -Dtest=SeleniumE2ETests

Write-Host ""
Write-Host "================================" -ForegroundColor Cyan
Write-Host "Test çalıştırması tamamlandı!" -ForegroundColor Cyan
Write-Host "================================" -ForegroundColor Cyan

