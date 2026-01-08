@echo off
echo =====================================
echo Selenium E2E Testleri (Visible Mode)
echo =====================================
echo.
echo Chrome tarayicisi acilacak ve testler otomatik olarak calisacak
echo.
mvnw.cmd test -Dtest=SeleniumE2ETests
echo.
echo =====================================
echo Test Tamamlandi!
echo =====================================
pause

