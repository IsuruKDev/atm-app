FROM maven:3.8.6-jdk-11
ADD target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]