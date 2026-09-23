# Poner esto en internet

El `Dockerfile` compila el proyecto y lo arranca, así que el hosting no necesita
tener Java ni Maven instalados. Vale para Render, Koyeb, Fly.io o cualquier sitio
que sepa construir una imagen de Docker.

## En Render

1. New → Web Service, y conecta este repositorio.
2. Runtime: **Docker**. Render encuentra el `Dockerfile` solo.
3. Instance Type: **Free**.
4. Añade las variables de abajo en Environment.

## Variables que hay que definir

| Variable | Qué es |
|---|---|
| `SPRING_DATASOURCE_URL` | La cadena JDBC completa, por ejemplo `jdbc:mysql://servidor:4000/biblioteca?sslMode=VERIFY_IDENTITY` |
| `SPRING_DATASOURCE_USERNAME` | Usuario de la base de datos |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña |

Spring Boot reconoce esos tres nombres por su cuenta y pisan lo que haya en
`application.properties`, así que no hace falta tocar el código.

`PORT` la pone el hosting; no la definas tú.

## Antes del primer arranque

La base de datos tiene que existir y tener las tablas. `spring.jpa.hibernate.ddl-auto`
está en `none` a propósito, así que **las tablas no se crean solas**: importa
`db/biblioteca.sql`, que trae el esquema y unos datos de ejemplo.

## Aviso sobre las capas gratuitas

En el plan gratuito de Render el servicio se duerme a los 15 minutos sin visitas.
La siguiente carga tarda alrededor de un minuto mientras arranca otra vez.
