# Система управління автосервісом

## Опис проекту
Система управління автосервісом - це веб-додаток, розроблений на базі Spring Boot, що забезпечує комплексне управління діяльністю автосервісу. Система дозволяє керувати клієнтами, автомобілями, записами на обслуговування, майстрами та виконаними роботами з повною підтримкою безпеки та автентифікації користувачів.

## Функціональність

### Основні бізнес-процеси:
* Управління клієнтами (додавання, редагування, видалення)
* Управління автомобілями клієнтів
* Управління майстрами та їх спеціалізаціями
* Створення та керування записами на обслуговування
* Керування типами послуг та їх цінами
* Перегляд статистики та звітності

### Система безпеки:
* Реєстрація користувачів з логіном та паролем
* Автентифікація за допомогою логіну та паролю
* JWT-токени для безпечного доступу до API
* Авторизація на основі ролей користувачів
* Захист REST API endpoints

## Архітектура проекту
Проект побудований з використанням Spring Boot і дотримується шаблону REST API з повною підтримкою Spring Security. Система використовує:
* **Spring Boot** - основний фреймворк
* **Spring Data JPA** для роботи з базою даних
* **Spring Security** для автентифікації та авторизації
* **JWT (JSON Web Tokens)** для безпечної передачі даних
* **PostgreSQL** в якості СУБД
* **Hibernate ORM** для об'єктно-реляційного мапінгу

## Розгортання
Проект розгорнуто на платформі **Render**:
* База даних: **Render PostgreSQL**
* Веб-сервіс: **Spring Boot application**

## Сутності системи

### Client (Клієнт)
- `id`: Long - унікальний ідентифікатор
- `name`: String - ім'я клієнта
- `phone`: String - номер телефону
- `email`: String - електронна пошта

### Car (Автомобіль)
- `id`: Long - унікальний ідентифікатор
- `client`: Client - власник автомобіля
- `make`: String - марка автомобіля
- `model`: String - модель автомобіля
- `year`: Integer - рік випуску
- `vin`: String - VIN-код автомобіля

### Mechanic (Майстер)
- `id`: Long - унікальний ідентифікатор
- `name`: String - ім'я майстра
- `specialization`: String - спеціалізація

### ServiceRecord (Запис на обслуговування)
- `id`: Long - унікальний ідентифікатор
- `car`: Car - автомобіль, що обслуговується
- `mechanic`: Mechanic - майстер, що виконує обслуговування
- `date`: LocalDate - дата обслуговування
- `description`: String - опис робіт

### ServiceType (Тип послуги)
- `id`: Long - унікальний ідентифікатор
- `name`: String - назва послуги
- `standardPrice`: BigDecimal - стандартна ціна

### User (Користувач) - Нова сутність
- `id`: Long - унікальний ідентифікатор
- `username`: String - логін користувача
- `password`: String - пароль (хешований)
- `email`: String - електронна пошта
- `role`: Role - роль користувача (ADMIN, USER, MECHANIC)

### Role (Роль) - Нова сутність
- `id`: Long - унікальний ідентифікатор
- `name`: String - назва ролі
- `authorities`: Set<Authority> - права доступу

## API Endpoints

### Автентифікація та авторизація
1. **POST** `/api/auth/register` - Реєстрація нового користувача
2. **POST** `/api/auth/login` - Вхід користувача (отримання JWT токену)
3. **POST** `/api/auth/refresh` - Оновлення JWT токену
4. **GET** `/api/auth/profile` - Отримання профілю поточного користувача
5. **PUT** `/api/auth/profile` - Оновлення профілю користувача

### Клієнти (потребує автентифікації)
6. **POST** `/api/clients` - Додати нового клієнта
7. **GET** `/api/clients` - Отримати список клієнтів
8. **PUT** `/api/clients/{id}` - Оновити дані клієнта
9. **DELETE** `/api/clients/{id}` - Видалити клієнта (тільки ADMIN)

### Автомобілі (потребує автентифікації)
10. **POST** `/api/clients/{clientId}/cars` - Додати автомобіль клієнта
11. **GET** `/api/clients/{clientId}/cars` - Отримати автомобілі клієнта
12. **PUT** `/api/cars/{id}` - Оновити дані автомобіля
13. **DELETE** `/api/cars/{id}` - Видалити автомобіль

### Майстри (потребує автентифікації)
14. **POST** `/api/mechanics` - Додати нового майстра (тільки ADMIN)
15. **GET** `/api/mechanics` - Отримати список майстрів
16. **PUT** `/api/mechanics/{id}` - Оновити майстра (тільки ADMIN)
17. **DELETE** `/api/mechanics/{id}` - Видалити майстра (тільки ADMIN)

### Записи на обслуговування (потребує автентифікації)
18. **POST** `/api/servicerecords` - Створити запис на обслуговування
19. **GET** `/api/cars/{carId}/servicerecords` - Отримати записи обслуговування автомобіля
20. **GET** `/api/mechanics/{mechanicId}/servicerecords` - Отримати записи обслуговування майстра
21. **PUT** `/api/servicerecords/{id}` - Оновити запис
22. **DELETE** `/api/servicerecords/{id}` - Видалити запис

### Типи послуг (потребує автентифікації)
23. **POST** `/api/servicetypes` - Додати тип послуги (тільки ADMIN)
24. **GET** `/api/servicetypes` - Отримати всі типи послуг
25. **POST** `/api/servicerecords/{recordId}/servicetypes/{typeId}` - Призначити тип послуги запису

### Статистика та звіти (потребує автентифікації)
26. **GET** `/api/cars/{carId}/total-service-cost` - Отримати загальну суму обслуговування автомобіля
27. **GET** `/api/mechanics/{mechanicId}/statistics` - Отримати статистику роботи майстра
28. **GET** `/api/servicetypes/popular` - Отримати найпопулярніші послуги
29. **GET** `/api/servicerecords?startDate={startDate}&endDate={endDate}` - Отримати обслуговування за période
30. **GET** `/api/cars/most-serviced` - Отримати автомобілі з найчастішим обслуговуванням

## Система безпеки

### JWT Authentication
Система використовує JWT токени для автентифікації. Кожен запит до захищених endpoint'ів повинен містити header:
```
Authorization: Bearer <JWT_TOKEN>
```

### Ролі користувачів
- **ADMIN** - повний доступ до всіх функцій системи
- **USER** - обмежений доступ до перегляду та створення записів
- **MECHANIC** - доступ до записів обслуговування та статистики

### Приклад запиту для реєстрації
```json
POST /api/auth/register
{
    "username": "johndoe",
    "password": "securePassword123",
    "email": "john@example.com",
    "role": "USER"
}
```

### Приклад запиту для входу
```json
POST /api/auth/login
{
    "username": "johndoe",
    "password": "securePassword123"
}
```

### Відповідь з JWT токеном
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer",
    "username": "johndoe",
    "email": "john@example.com",
    "roles": ["USER"]
}
```

## Налаштування бази даних

Проект використовує PostgreSQL базу даних на Render із наступними налаштуваннями:

```yaml
spring:
  jpa:
    database: POSTGRESQL
    show-sql: true
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect
  datasource:
    url: jdbc:postgresql://dpg-d11c09ali9vc738kevt0-a.oregon-postgres.render.com:5432/db_postgres2_fpw3
    username: db_postgres2_fpw3_user
    password: r3MoiVgnE6PX34S8AyrVu8iEm8OZjpJs
    driverClassName: org.postgresql.Driver
  sql:
    init:
      platform: postgres
  security:
    jwt:
      secret: mySecretKey
      expiration: 86400000 # 24 hours
```


## Локальний запуск проекту

1. Переконайтеся, що у вас встановлено **Java 17** або новішу версію
2. Клонуйте репозиторій проекту
3. Налаштуйте змінні середовища для JWT:
   ```bash
   export JWT_SECRET=your-secret-key
   export JWT_EXPIRATION=86400000
   ```
4. Запустіть застосунок за допомогою команди:
   ```bash
   ./mvnw spring-boot:run
   ```
   або
   ```bash
   mvn spring-boot:run
   ```

## Тестування API

### Реєстрація користувача
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123",
    "email": "test@example.com",
    "role": "USER"
  }'
```

### Вхід користувача
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

### Доступ до захищених endpoint'ів
```bash
curl -X GET http://localhost:8080/api/clients \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Технології

### Backend
* **Java 17**
* **Spring Boot 3.2.0**
* **Spring Data JPA**
* **Spring Security**
* **PostgreSQL**
* **Hibernate**
* **JWT (jjwt 0.11.5)**
* **Lombok**
* **Maven** (система збірки)

### Infrastructure
* **Render** (хостинг бази даних та додатку)
* **PostgreSQL** (віддалена база даних)

## Безпека

### Хешування паролів
Паролі користувачів хешуються за допомогою BCrypt алгоритму з cost factor 12.

### JWT Security
- Токени підписуються секретним ключем
- Термін дії токенів: 24 години
- Токени містять інформацію про користувача та його ролі

### CORS
Налаштовано CORS для можливості доступу з frontend додатків.

## Варіант реалізації

Система розроблена відповідно до **варіанту 12** курсової роботи з повною реалізацією першої та другої частини, включаючи:

### Перша частина (завершена):
- Імпортування Spring Boot-проєкту
- Підключення віддаленої бази даних на Render
- Створення класів сутностей з анотаціями та Lombok
- Налаштування реляційних відношень (1:1, 1:N, M:N)
- Програмування REST-контролерів
- Програмування сервісів та репозиторіїв
- Тестування REST-запитів
- Публікація на Github та Render

### Друга частина (завершена):
- Додавання реєстрації користувача за допомогою логіну та паролю
- Додавання автентифікації за допомогою логіну та паролю
- Додавання автентифікації за допомогою JWT-токену
- Реалізація системи ролей та авторизації
- Захист API endpoints
- Тестування веб-додатку з безпекою
- Публікація оновленого проекту

**Автор: Іщенко Дмитро**