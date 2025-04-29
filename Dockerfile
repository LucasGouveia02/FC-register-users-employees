FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

EXPOSE ${PORT}

# Copie o JAR para o container
COPY target/register-users-employees-0.0.1-SNAPSHOT.jar /app/register-users-employees.jar

# Defina o comando para rodar o JAR
ENTRYPOINT ["java", "-jar", "/app/register-users-employees.jar"]