FROM adoptopenjdk/openjdk11:latest
ADD target/readingisgood-0.0.1-SNAPSHOT.jar readingisgood-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "readingisgood-0.0.1-SNAPSHOT.jar"]