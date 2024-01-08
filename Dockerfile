FROM gradle:latest as builder

WORKDIR /app

VOLUME ["~/.gradle/caches", "/app/.gradle"]
COPY . .
RUN ["gradle", "build"]

FROM openjdk:latest

COPY --from=builder /app/build/libs/holaluz-0.0.1-SNAPSHOT.jar /app/app.jar
##COPY --from=builder /app/src/main/resources/report.jrxml /app/report.jrxml
EXPOSE 8080
ENTRYPOINT ["java", "-server", "-jar", "/app/app.jar", "-web -webAllowOthers -tcp -tcpAllowOthers -browser"]

