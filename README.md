Система управления задачами (Task Management System)
Описание
Простое API для управления задачами, комментариями и пользователями.
Включает аутентификацию по JWT, ролевую систему (админ/пользователь), фильтрацию, пагинацию, валидацию и Swagger UI.

Запуск проекта
Требования

Java 17+

Docker и Docker Compose

Сборка и запуск

bash
mvn clean package
docker-compose up -d
Доступ к API

Swagger UI: http://localhost:8080/swagger-ui/index.html

API: http://localhost:8080/api/tasks

JWT-токен: /auth/login

Тестирование

bash
mvn test
Конфигурация
Порт: 8080

БД: PostgreSQL (автоматически поднимается через Docker Compose)

Логин/пароль: см. настройки в application.properties