FROM amazoncorretto:21

WORKDIR /app

COPY target/BankDataAPI-0.0.1-SNAPSHOT.jar /app/springboot-app.jar
COPY swift_codes.csv /app/

ENTRYPOINT ["java", "-jar", "springboot-app.jar"]
