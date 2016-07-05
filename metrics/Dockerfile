FROM java:8-jre

COPY target/metrics-1.0-SNAPSHOT.jar /metrics.jar

CMD java -jar /metrics.jar --spring.profiles.active="docker-compose"
