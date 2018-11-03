FROM openjdk:8-jre-alpine

USER 10001

COPY target/spring-cloud-function-*-aws.jar /opt/app.jar
EXPOSE 8080

CMD ["/usr/bin/java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=local", "-jar", "/opt/app.jar"]