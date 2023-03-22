FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} aws-sqs-intergrated-microservice.jar

ENTRYPOINT ["java", "-jar", "/aws-sqs-intergrated-microservice.jar"]

EXPOSE 8080