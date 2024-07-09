FROM openjdk:17-oracle
COPY target/*.jar rdv_expert.jar
EXPOSE 8089
LABEL authors="user"

ENTRYPOINT ["java", "-jar","rdv_expert.jar"]