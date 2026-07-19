FROM eclipse-temurin:25 AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw -B -ntp dependency:go-offline
COPY src ./src
RUN ./mvnw -B -ntp clean package

FROM eclipse-temurin:25
RUN groupadd --system heating && useradd --system --gid heating backend
WORKDIR /app
COPY --from=build --chown=backend:heating /app/target/*.jar application.jar
USER backend
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
