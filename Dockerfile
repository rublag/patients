FROM openjdk:15
COPY target/uberjar/patients.jar /usr/patients.jar
WORKDIR /usr
CMD ["java", "-jar", "patients.jar"]
