FROM java:8-jre

COPY target/webapp-1.0-SNAPSHOT.jar /webapp.jar

CMD java -jar /webapp.jar --spring.profiles.active="docker-compose"
