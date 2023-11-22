FROM azul/zulu-openjdk:21-jre-latest
COPY . .
RUN ./mvnw clean install -DskipTests
ENTRYPOINT ["java", "-jar", "target/financial-transactions-0.0.1-SNAPSHOT.jar"]
