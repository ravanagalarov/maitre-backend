# Maitre - Backend Project

Bu layihə restoranlar üçün məhsul idarəetməsi, sifarişlərin qəbulu, statusların dəyişdirilməsi və satış statistikalarını idarə edən Spring Boot tətbiqidir.

---

# Layihəni necə işə salmaq olar?

Tətbiq Docker vasitəsilə konteynerləşdirilib. Kompüterdə Java və ya PostgreSQL quraşdırmağa ehtiyac yoxdur.

1. Layihəni klonlayın:
   ```bash
   git clone [https://github.com/ravanagalarov/maitre-backend.git](https://github.com/ravanagalarov/maitre-backend.git)
   cd maitre-backend

Əsas qərarlar və onların səbəbləri
Docker & Docker Compose: Layihənin hər mühitdə rahat və eyni şəkildə çalışması üçün tətbiq və PostgreSQL bazası Docker daxilində quraşdırıldı.

Layered Architecture: Kodun oxunması və idarə olunması rahat olsun deyə klassik Controller -> Service -> Repository strukturu seçildi.

DTO və Validation: Baza obyektlərini birbaşa xaricə çıxarmamaq üçün DTO-lardan istifadə olundu. Daxil olan məlumatlar @Valid ilə yoxlanılır.

DataInitializer: Tətbiq ilk dəfə işə düşəndə bazaya avtomatik 2 restoran əlavə olunur ki, Postman-də testləri birbaşa aparmaq mümkün olsun.

Tələblərdəki açıq məqamlar üçün verdiyim qərarlar
Restoran məlumatları:

Məhsul və sifarişlər restaurantId-yə bağlı idi, amma restoran yaratmaq üçün xüsusi endpoint tələb olunmurdu. Buna görə DataInitializer ilə sistemə avtomatik 2 ilkin restoran (ID: 1 və ID: 2) əlavə etdim.

Sifariş statusları:

Status keçidləri üçün məntiqi sıra təyin olundu: PENDING -> PREPARING -> COMPLETED / CANCELLED. Tamamlanmış və ya ləğv edilmiş sifarişin statusunu sonradan dəyişmək olmur.

Top-Selling statistikası:

Ən çox satılan məhsullar sorğusunda ilkin olaraq ən çox sifariş verilmiş ilk 5 məhsulun qaytarılması qərara alındı.

Tapşırığa nə qədər vaxt sərf olundu?
Bu tapşırığın hazırlanmasına, arxitekturanın qurulmasına və Postman ilə test edilməsinə ümumilikdə 1-2 gün vaxt sərf olundu.


Daha çox vaxtım olsaydı, nəyi dəyişərdim?
Spring Security & Authentication: İstifadəçilər və adminlər üçün giriş-çıxış (login/register) və autorizasiya sistemini qurardım.

OTP Kod & Şifrə Əməliyyatları: Qeydiyyat və ya şifrəni unutduqda (forgot password) istifadəçiyə OTP kod göndərilməsi və şifrə dəyişmə funksionallığını əlavə edərdim.

Redis Caching: Tez-tez müraciət olunan məlumatları və top-selling kimi statistikaları Redis daxilində keşləyərək bazanın yükünü azaldardım.

Testlər: Biznes məntiqini daha dərindən yoxlamaq üçün Unit və Integration testlərin sayını artırardım.