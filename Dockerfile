FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY build/libs/Myorganization-0.1-all.jar /app/
EXPOSE 8080
CMD ["java", "-jar", "Myorganization-0.1-all.jar"]