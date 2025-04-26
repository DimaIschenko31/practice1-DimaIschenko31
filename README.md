# Система управління автосервісом

## Опис проекту
Система управління автосервісом - це веб-додаток, розроблений на базі Spring Boot, що забезпечує комплексне управління діяльністю автосервісу. Система дозволяє керувати клієнтами, автомобілями, записами на обслуговування, майстрами та виконаними роботами.

## Функціональність
Система реалізує наступні бізнес-процеси:
* Управління клієнтами (додавання, редагування, видалення)
* Управління автомобілями клієнтів
* Управління майстрами та їх спеціалізаціями
* Створення та керування записами на обслуговування
* Керування типами послуг та їх цінами
* Перегляд статистики та звітності

## Архітектура проекту
Проект побудований з використанням Spring Boot і дотримується шаблону REST API. Система використовує:
* **Spring Data JPA** для роботи з базою даних
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

## API Endpoints

### Клієнти
1. **POST** `/api/clients` - Додати нового клієнта
2. **GET** `/api/clients` - Отримати список клієнтів
3. **PUT** `/api/clients/{id}` - Оновити дані клієнта
4. **DELETE** `/api/clients/{id}` - Видалити клієнта

### Автомобілі
5. **POST** `/api/clients/{clientId}/cars` - Додати автомобіль клієнта
6. **GET** `/api/clients/{clientId}/cars` - Отримати автомобілі клієнта
7. **PUT** `/api/cars/{id}` - Оновити дані автомобіля
8. **DELETE** `/api/cars/{id}` - Видалити автомобіль

### Майстри
9. **POST** `/api/mechanics` - Додати нового майстра
10. **GET** `/api/mechanics` - Отримати список майстрів
11. **PUT** `/api/mechanics/{id}` - Оновити майстра
12. **DELETE** `/api/mechanics/{id}` - Видалити майстра

### Записи на обслуговування
13. **POST** `/api/servicerecords` - Створити запис на обслуговування
14. **GET** `/api/cars/{carId}/servicerecords` - Отримати записи обслуговування автомобіля
15. **GET** `/api/mechanics/{mechanicId}/servicerecords` - Отримати записи обслуговування майстра
16. **PUT** `/api/servicerecords/{id}` - Оновити запис
17. **DELETE** `/api/servicerecords/{id}` - Видалити запис

### Типи послуг
18. **POST** `/api/servicetypes` - Додати тип послуги
19. **GET** `/api/servicetypes` - Отримати всі типи послуг
20. **POST** `/api/servicerecords/{recordId}/servicetypes/{typeId}` - Призначити тип послуги запису

### Статистика та звіти
21. **GET** `/api/cars/{carId}/total-service-cost` - Отримати загальну суму обслуговування автомобіля
22. **GET** `/api/mechanics/{mechanicId}/statistics` - Отримати статистику роботи майстра
23. **GET** `/api/servicetypes/popular` - Отримати найпопулярніші послуги
24. **GET** `/api/servicerecords?startDate={startDate}&endDate={endDate}` - Отримати обслуговування за період
25. **GET** `/api/cars/most-serviced` - Отримати автомобілі з найчастішим обслуговуванням

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
    url: jdbc:postgresql://dpg-d050bvbe5dus738p6gcg-a.oregon-postgres.render.com/db_postgres_86tg
    username: db_postgres_86tg_user
    password: vhIk0yvKowTsaqHpWah8a6piGdX7e6Oe
    driverClassName: org.postgresql.Driver
  sql:
    init:
      platform: postgres
```

## Локальний запуск проекту

1. Переконайтеся, що у вас встановлено **Java 11** або новішу версію
2. Клонуйте репозиторій проекту
3. Запустіть застосунок за допомогою команди:
   ```bash
   ./mvnw spring-boot:run
   ```
   або
   ```bash
   mvn spring-boot:run
   ```

## Технології

* **Java**
* **Spring Boot**
* **Spring Data JPA**
* **PostgreSQL**
* **Hibernate**
* **Maven/Gradle** (система збірки)
* **Render** (хостинг)

## Варіант реалізації

Система розроблена відповідно до **варіанту 12** курсової роботи.

# Посилання на розгорнутий проект
* http://localhost:8080/api/clients

* http://localhost:8080/api/mechanics

* http://localhost:8080/api/service-types

* http://localhost:8080/api/cars/1/service-records

* http://localhost:8080/api/cars/1/total-service-amount

* http://localhost:8080/api/service-types/popular


 **Іщенко Дмитро** 
