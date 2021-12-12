FROM openjdk:15

ARG JAR_FILE=target/*.jar
ADD ./target/readingisgood-0.0.1-SNAPSHOT.jar reading-is-good.jar

ENTRYPOINT ["java", "-jar", "/reading-is-good.jar"]