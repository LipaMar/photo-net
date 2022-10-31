FROM openjdk:17-alpine

COPY .mvn ./.mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

COPY src ./src

EXPOSE 8080

CMD ["./mvnw","spring-boot:run"]

