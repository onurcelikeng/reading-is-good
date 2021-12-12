FROM openjdk:15
ADD target/reading-is-good-0.0.1-SNAPSHOT.jar reading-is-good-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "reading-is-good-0.0.1-SNAPSHOT.jar"]