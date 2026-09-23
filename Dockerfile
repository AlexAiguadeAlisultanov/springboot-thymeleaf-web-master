# Primera fase: compilar. Se hace dentro de la imagen para que no dependa de lo
# que haya instalado en la maquina que despliega.
FROM maven:3.9-eclipse-temurin-21 AS construccion
WORKDIR /app

# Las dependencias primero y en su propia capa: mientras el pom no cambie, las
# reconstrucciones se saltan la descarga entera.
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B -q -DskipTests package

# Segunda fase: ejecutar. Solo el jar y un JRE, sin Maven ni codigo fuente.
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=construccion /app/target/*.jar app.jar

# Render (y la mayoria de hostings) dicen en PORT en que puerto hay que escuchar.
ENV PORT=8080
EXPOSE 8080

# El contenedor no es la maquina: hay que escuchar en todas las interfaces.
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -Dserver.address=0.0.0.0 -jar app.jar"]
