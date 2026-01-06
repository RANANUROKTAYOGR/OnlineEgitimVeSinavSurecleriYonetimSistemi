OnlineEgitimVeSinavSurecleriYonetimSistemi (OESYS)

Bu README, projeyi yerel makinada derleme, birim ve entegrasyon testlerini çalıştırma ve debug çıktısı toplama adımlarını içerir.

Özet (ne yapıyorum)
- Bu çalışma ortamında Docker/Testcontainers çalıştırılamadığı için entegrasyon testlerini doğrudan burada çalıştıramadım.
- Aşağıda PowerShell için kesin adımlar ve beklenen çıktılar yer alıyor; lütfen bu adımları kendi makinenizde çalıştırıp elde ettiğiniz çıktıları buraya yapıştırın. Ben de gelen çıktılara göre repo üzerinde düzenlemeleri yapacağım.

Gereksinimler
- Java 21 (yüklü olduğu doğrulandı)
- Maven (proje mvnw kullanıyor; wrapper mevcuttur)
- Docker (entegrasyon testleri için gerekli)
- İnternet bağlantısı (Testcontainers manifestleri ve Docker image'lar için)

Hızlı Başlangıç (PowerShell)
1) Docker kontrolü (Testcontainers için mutlaka çalışıyor olmalı)
```powershell
docker version
docker info
```
- Beklenen: Docker Engine ve Server bilgileri; eğer "Cannot connect to the Docker daemon" gibi bir hata alırsanız Docker Desktop'ı başlatın.

2) Sadece birim testleri (integration testleri atla)
```powershell
# Bu komut önce temizler, bağımlılıkları indirir ve sadece unit testleri çalıştırır
.\mvnw.cmd -DskipITs=true clean test
```
- Eğer başarısız olursa debug çıktısını dosyaya yazın ve paylaşın:
```powershell
.\mvnw.cmd -DskipITs=true -X clean test > mvn-unit-debug.txt 2>&1
Get-Content mvn-unit-debug.txt -Raw
```

3) Entegrasyon testlerini (Postgres via Testcontainers) çalıştırma
> Not: Docker çalışıyor olmalı
```powershell
.\mvnw.cmd -DskipITs=false clean verify
```
- Debug/log dosyasına yazmak isterseniz:
```powershell
.\mvnw.cmd -DskipITs=false -X clean verify > mvn-verify-debug.txt 2>&1
Get-Content mvn-verify-debug.txt -TotalCount 400
```

4) Test raporları
- Unit test raporları: `target/surefire-reports/`
- Integration test raporları (failsafe): `target/failsafe-reports/`

Raporları görüntülemek için (PowerShell):
```powershell
dir .\target\surefire-reports
Get-Content .\target\surefire-reports\*.txt -Raw

dir .\target\failsafe-reports
Get-Content .\target\failsafe-reports\*.txt -Raw
```

Sık Karşılaşılan Hatalar ve Çözümleri
- Docker daemon unreachable / permission denied
  - Docker Desktop'ı başlatın veya Docker servisinin çalıştığını doğrulayın.
  - Windows: Docker Desktop'ı yönetici olarak başlatmak yardımcı olabilir.

- Testcontainers image çekilemiyor (network/proxy)
  - Proxy ayarlarınızı kontrol edin veya doğrudan internet erişimi sağlayın.

- Maven derleme hataları (compile errors)
  - `.
  mvnw.cmd -DskipITs=true -X clean test` çıktısını paylaşın; ben hataya göre gerekli sınıf/düzenlemeyi repo içinde yaparım.

- Uzun debug çıktısı
  - `> mvn-verify-debug.txt 2>&1` ile dosyaya yönlendirin ve dosyayı paylaşın.

Benim tarafımdan yapılabilecekler
- Siz debug çıktısını gönderdiğinizde: stacktrace'leri analiz edip gerekli kod değişikliklerini (entity ilişkileri, service impl eksikleri, repository düzeltmeleri, test mocklamaları) doğrudan repoya ekleyeceğim.
- Entegre testlerin Docker hatalarına dair çözümler/CI önerileri vereceğim.

Notlar
- `src/test/resources/application-test.properties` proje içinde Testcontainers JDBC URL ile hazır — `jdbc:tc:postgresql:14.5:///testdb`.
- Entegre testler Docker gerektirdiği için CI veya local Docker ile çalıştırılmalıdır.

Sonraki adım (size düşen)
- Yukarıdaki adımları çalıştırıp çıktı dosyalarını (veya konsol çıktısını) buraya yapıştırın: özellikle `mvn-verify-debug.txt` veya `mvn-unit-debug.txt` çok yardımcı olacak.
- Ben çıktı geldikten sonra hataları düzeltecek ve repo üzerinde değişiklikleri uygulayacağım.

İsterseniz şimdi repo üzerinde kalan küçük temizlemeleri (unused imports, test mock düzenlemeleri) otomatik olarak yaparım; onay verin yeter.

