FROM openjdk:17
ADD target/medilabo_patient-0.0.1-SNAPSHOT.jar medilabo_patient.jar
ENTRYPOINT ["java","-jar","/medilabo_patient.jar"]
EXPOSE 8081