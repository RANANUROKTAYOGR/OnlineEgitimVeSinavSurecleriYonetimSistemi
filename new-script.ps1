# Yeni Script Dosyasi

Write-Host "Yeni bir script dosyasi olusturuldu." -ForegroundColor Cyan

# Docker build ve tag islemleri
Write-Host "Docker image olusturuluyor..." -ForegroundColor Yellow
docker build -t oesys-app:21 .
docker tag oesys-app:21 oesys-app:latest

Write-Host "Script tamamlandi." -ForegroundColor Green
