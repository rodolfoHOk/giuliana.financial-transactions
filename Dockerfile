FROM eclipse-temurin:21-jammy
COPY . .
RUN ./mvnw clean install -DskipTests
ENTRYPOINT ["java", "-jar", "target/financial-transactions-0.0.1-SNAPSHOT.jar"]
