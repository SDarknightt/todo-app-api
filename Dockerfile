FROM eclipse-temurin:21-jdk-alpine as build

WORKDIR /app

COPY .mvn .mvn

COPY ./mvnw ./mvnw

RUN chmod +x mvnw

COPY pom.xml ./pom.xml

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine as runtime

WORKDIR /app

COPY --from=build app/target/todoapi-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]