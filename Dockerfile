FROM openjdk:19-alpine
WORKDIR /app
COPY target/rdv-expert.jar app.jar
EXPOSE 9898
ENTRYPOINT ["java", "-jar", "app.jar"]
