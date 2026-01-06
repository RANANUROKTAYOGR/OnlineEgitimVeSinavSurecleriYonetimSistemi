Bu proje için testleri ve raporları yerelde üretmek için:

1) Birim testleri çalıştır:

```powershell
./mvnw clean test
```

Raporlar `target/surefire-reports` içinde olacak.

2) Entegrasyon testleri (IT) dahil tüm testleri çalıştır ve coverage al:

```powershell
./mvnw -DskipITs=false verify
```

Raportlar:
- Birim test raporları: `target/surefire-reports`
- Entegrasyon test raporları: `target/failsafe-reports`
- JaCoCo coverage HTML raporu: `target/site/jacoco/index.html`

3) Eğer CI kullanıyorsanız (ör. GitHub Actions), `.github/workflows/ci.yml` dosyası testleri çalıştırır ve raporları artifact olarak yükler.

Sorun yaşarsanız `./mvnw -e -X verify` ile ayrıntılı çıktı alıp bana gönderin; ben hatayı burada düzelteceğim.

