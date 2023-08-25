FROM adoptopenjdk/openjdk11:latest
RUN mkdir /opt/app
COPY target/*.jar /opt/app/app.jar
CMD ["java", "-jar", "/opt/app/app.jar"]