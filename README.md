# 🔐 Redis-Backed Token Isolation with Spring Boot

Bu proje, dağıtık sistemlerde güvenli kimlik doğrulama amacıyla geliştirilmiştir. 
JWT token doğrudan client'a verilmez; onun yerine Redis üzerinde tutulan 2 seviyeli anahtar yapısı kullanılır.

## 🚀 Amaç

- Token'ı doğrudan client'a vermemek
- Redis üzerinden erişilen, izole edilmiş ve kontrollü bir authentication yapısı kurmak
- Token sızıntılarını önlemek
- Kolayca expire, logout, ve refresh yönetimi yapılabilen esnek bir yapı oluşturmak

---

## ⚙️ Teknolojiler

- Java 21
- Spring Boot 3.5.3
- Spring Security 6
- Redis (Docker ile)
- PostgreSQL (Kullanıcı verisi için)
- JJWT (v0.11.5)

---

## 🧠 Giriş Mantığı (2 Katmanlı Redis Token Akışı)

1 - User → /api/auth/login → email, password

2 - Server → JWT oluşturur

3 - Redis:

    public_key -> private_key

    private_key -> jwt_token

4 - Client → Sadece public_key bilir

5 - İsteklerde:

    X-PUBLIC-KEY: {public_key}

6 - Server → Redis’ten token’ı zincirle çözer

---

## 🔓 Public Endpoint'ler

- `POST /api/auth/register`
- `POST /api/auth/login`

## 🔐 Korunan Endpoint'ler

- `GET /api/user/me` – Token'la kim olduğunu öğren
- `POST /api/auth/logout` – Session sonlandırılır

---

## 🔁 Logout Mantığı

Client `X-PUBLIC-KEY` header'ı ile `/api/auth/logout` çağırır:  
Redis’ten hem `public_key` hem `private_key` hem `jwt` silinir.

---

## ⏳ Expiration

- JWT token `application.yml` içindeki `jwt.expiration` kadar geçerli
- Redis verileri de bu süre boyunca TTL ile saklanır
- Süre dolunca otomatik silinir

---
