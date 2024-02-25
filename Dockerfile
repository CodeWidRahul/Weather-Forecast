FROM openjdk:17
COPY build/libs/Weather Forecast-1.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8888
ENTRYPOINT ["java","-jar","Weather Forecast-1.0-SNAPSHOT.jar"]