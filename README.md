**Опис проекту**
Система управління автосервісом - це веб-додаток, розроблений на базі Spring Boot, що забезпечує комплексне управління діяльністю автосервісу. Система дозволяє керувати клієнтами, автомобілями, записами на обслуговування, майстрами та виконаними роботами.
Функціональність
Система реалізує наступні бізнес-процеси:

Управління клієнтами (додавання, редагування, видалення)
Управління автомобілями клієнтів
Управління майстрами та їх спеціалізаціями
Створення та керування записами на обслуговування
Керування типами послуг та їх цінами
Перегляд статистики та звітності

Архітектура проекту
Проект побудований з використанням Spring Boot і дотримується шаблону REST API. Система використовує:

Spring Data JPA для роботи з базою даних
PostgreSQL в якості СУБД
Hibernate ORM для об'єктно-реляційного мапінгу

Сутності системи
Client (Клієнт)

id: Long - унікальний ідентифікатор
name: String - ім'я клієнта
phone: String - номер телефону
email: String - електронна пошта

Car (Автомобіль)

id: Long - унікальний ідентифікатор
client: Client - власник автомобіля
make: String - марка автомобіля
model: String - модель автомобіля
year: Integer - рік випуску
vin: String - VIN-код автомобіля

Mechanic (Майстер)

id: Long - унікальний ідентифікатор
name: String - ім'я майстра
specialization: String - спеціалізація

ServiceRecord (Запис на обслуговування)

id: Long - унікальний ідентифікатор
car: Car - автомобіль, що обслуговується
mechanic: Mechanic - майстер, що виконує обслуговування
date: LocalDate - дата обслуговування
description: String - опис робіт

ServiceType (Тип послуги)

id: Long - унікальний ідентифікатор
name: String - назва послуги
standardPrice: BigDecimal - стандартна ціна

API Endpoints
Клієнти

POST /api/clients - Додати нового клієнта
GET /api/clients - Отримати список клієнтів
PUT /api/clients/{id} - Оновити дані клієнта
DELETE /api/clients/{id} - Видалити клієнта

Автомобілі

POST /api/clients/{clientId}/cars - Додати автомобіль клієнта
GET /api/clients/{clientId}/cars - Отримати автомобілі клієнта
PUT /api/cars/{id} - Оновити дані автомобіля
DELETE /api/cars/{id} - Видалити автомобіль

Майстри

POST /api/mechanics - Додати нового майстра
GET /api/mechanics - Отримати список майстрів
PUT /api/mechanics/{id} - Оновити майстра
DELETE /api/mechanics/{id} - Видалити майстра

Записи на обслуговування

POST /api/servicerecords - Створити запис на обслуговування
GET /api/cars/{carId}/servicerecords - Отримати записи обслуговування автомобіля
GET /api/mechanics/{mechanicId}/servicerecords - Отримати записи обслуговування майстра
PUT /api/servicerecords/{id} - Оновити запис
DELETE /api/servicerecords/{id} - Видалити запис

Типи послуг

POST /api/servicetypes - Додати тип послуги
GET /api/servicetypes - Отримати всі типи послуг
POST /api/servicerecords/{recordId}/servicetypes/{typeId} - Призначити тип послуги запису

Статистика та звіти

GET /api/cars/{carId}/total-service-cost - Отримати загальну суму обслуговування автомобіля
GET /api/mechanics/{mechanicId}/statistics - Отримати статистику роботи майстра
GET /api/servicetypes/popular - Отримати найпопулярніші послуги
GET /api/servicerecords?startDate={startDate}&endDate={endDate} - Отримати обслуговування за період
GET /api/cars/most-serviced - Отримати автомобілі з найчастішим обслуговуванням

Налаштування бази даних
Проект використовує PostgreSQL базу даних із наступними налаштуваннями:
yamlspring:
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
Запуск проекту

Переконайтеся, що у вас встановлено Java 11 або новішу версію
Клонуйте репозиторій проекту
Запустіть застосунок за допомогою команди:
./mvnw spring-boot:run
або
mvn spring-boot:run


Технології

Java
Spring Boot
Spring Data JPA
PostgreSQL
Hibernate
Maven/Gradle (система збірки
