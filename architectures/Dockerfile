FROM java:8-jre

COPY target/architectures-1.0-SNAPSHOT.jar /architectures.jar

CMD java -jar /architectures.jar --spring.profiles.active="docker-compose"
