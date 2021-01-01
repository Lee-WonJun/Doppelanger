FROM openjdk:8-alpine

COPY target/uberjar/doppelanger.jar /doppelanger/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/doppelanger/app.jar"]
