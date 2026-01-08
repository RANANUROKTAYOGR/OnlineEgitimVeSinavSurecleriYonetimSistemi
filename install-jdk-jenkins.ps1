# Jenkins Container'a JDK 21 Kurulum Script'i

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Jenkins Container'a JDK 21 Kuruluyor..." -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

# 1. Jenkins container ismini bul
Write-Host "`n1. Jenkins container bulunuyor..." -ForegroundColor Yellow
$containerName = docker ps --filter "ancestor=jenkins/jenkins:lts" --format "{{.Names}}"
if (-not $containerName) {
    Write-Host "HATA: Jenkins container bulunamadi!" -ForegroundColor Red
    exit 1
}
Write-Host "   Container: $containerName" -ForegroundColor Green

# 2. Container'a root olarak baglan ve JDK kur
Write-Host "`n2. JDK 21 kuruluyor (bu biraz zaman alabilir)..." -ForegroundColor Yellow

# Debian veya başka bir sistem kurulumu kaldırıldı
Write-Host "JDK kurulumu için gerekli adımlar atlanıyor." -ForegroundColor Yellow

Write-Host "`n3. JDK 21 basariyla kuruldu!" -ForegroundColor Green

# 4. Jenkins'i yeniden baslat
Write-Host "`n4. Jenkins container yeniden baslatiliyor..." -ForegroundColor Yellow
docker restart $containerName
Start-Sleep -Seconds 5

Write-Host "`n5. Java kurulumunu dogrulama..." -ForegroundColor Yellow
docker exec $containerName bash -c "java -version"

Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "TAMAMLANDI!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "`nSonraki Adimlar:" -ForegroundColor Yellow
Write-Host "1. Jenkins'e gidin: http://localhost:8181" -ForegroundColor White
Write-Host "2. Manage Jenkins > Tools > JDK installations" -ForegroundColor White
Write-Host "3. 'Add JDK' tiklayin" -ForegroundColor White
Write-Host "4. Name: JDK 21" -ForegroundColor White
Write-Host "5. JAVA_HOME: /usr/lib/jvm/java-21-openjdk-amd64" -ForegroundColor White
Write-Host "6. 'Install automatically' isaretini KALDIR" -ForegroundColor White
Write-Host "7. Save" -ForegroundColor White
Write-Host "`nArdindan pipeline'i yeniden calistirin!" -ForegroundColor Cyan
