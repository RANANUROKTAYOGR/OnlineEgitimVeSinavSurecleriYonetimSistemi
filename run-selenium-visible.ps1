# Selenium E2E Testlerini GÖRÜNÜR modda çalıştırır
# Chrome tarayıcı açılır ve testleri izleyebilirsiniz

Write-Host "=" -NoNewline -ForegroundColor Cyan
Write-Host ("=" * 79) -ForegroundColor Cyan
Write-Host "🚀 SELENIUM E2E TESTLERİ - GÖRÜNÜR MOD" -ForegroundColor Green
Write-Host "=" -NoNewline -ForegroundColor Cyan
Write-Host ("=" * 79) -ForegroundColor Cyan
Write-Host ""

# 1. Uygulamanın çalışıp çalışmadığını kontrol et
Write-Host "🔍 Sunucu kontrolü yapılıyor..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080" -Method Head -TimeoutSec 5 -ErrorAction Stop
    Write-Host "✅ Uygulama çalışıyor (HTTP $($response.StatusCode))" -ForegroundColor Green
} catch {
    Write-Host "❌ UYARI: Uygulama çalışmıyor!" -ForegroundColor Red
    Write-Host "⚠️  Lütfen önce uygulamayı başlatın:" -ForegroundColor Yellow
    Write-Host "   .\mvnw.cmd spring-boot:run" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Veya Docker ile:" -ForegroundColor Yellow
    Write-Host "   docker compose up" -ForegroundColor Cyan
    Write-Host ""
    exit 1
}

Write-Host ""
Write-Host "=" -NoNewline -ForegroundColor Cyan
Write-Host ("=" * 79) -ForegroundColor Cyan
Write-Host "🌐 CHROME TARAYICISI AÇILACAK" -ForegroundColor Magenta
Write-Host "   Testleri tarayıcı penceresinden izleyebilirsiniz!" -ForegroundColor Yellow
Write-Host "=" -NoNewline -ForegroundColor Cyan
Write-Host ("=" * 79) -ForegroundColor Cyan
Write-Host ""

# 2. Selenium testlerini çalıştır
Write-Host "🧪 Selenium testleri çalıştırılıyor..." -ForegroundColor Yellow
Write-Host ""

.\mvnw.cmd test -Dtest=SeleniumE2ETests

$exitCode = $LASTEXITCODE

Write-Host ""
Write-Host "=" -NoNewline -ForegroundColor Cyan
Write-Host ("=" * 79) -ForegroundColor Cyan
if ($exitCode -eq 0) {
    Write-Host "✅ TESTLER BAŞARIYLA TAMAMLANDI!" -ForegroundColor Green
} else {
    Write-Host "❌ TESTLER BAŞARISIZ OLDU!" -ForegroundColor Red
}
Write-Host "=" -NoNewline -ForegroundColor Cyan
Write-Host ("=" * 79) -ForegroundColor Cyan
Write-Host ""

exit $exitCode

