FROM eclipse-temurin:25 AS BUILD
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw -B clean package -DskipTests

FROM eclipse-temurin:25
WORKDIR /app
COPY --from=BUILD /app/target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
