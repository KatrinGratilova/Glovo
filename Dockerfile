FROM openjdk:22
EXPOSE 8080
ENV JAR_FILE=target/Glovo-*.jar
COPY ${JAR_FILE} /Glovo.jar
ENTRYPOINT ["java", "-jar", "/Glovo.jar"]