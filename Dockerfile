FROM node:21 AS ng-builder

RUN npm i -g @angular/cli

WORKDIR /ngapp

COPY toiletnearme/client/package*.json .
COPY toiletnearme/client/angular.json .
COPY toiletnearme/client/tsconfig.* .
# COPY frontend/server.ts .
COPY toiletnearme/client/src src

RUN npm ci
RUN ng build
# /ngapp/dist/docker/browser

# start on this linux server
FROM maven:3-eclipse-temurin-21 AS sb-builder

WORKDIR /sbapp

COPY toiletnearme/mvnw .
COPY toiletnearme/mvnw.cmd .
COPY toiletnearme/pom.xml .
COPY toiletnearme/.mvn .mvn
COPY toiletnearme/src src
COPY --from=ng-builder /ngapp/dist/client/browser/ /sbapp/src/main/resources/static

RUN mvn package -e -Dmaven.test.skip=true

FROM maven:3-eclipse-temurin-21
WORKDIR /app

COPY --from=sb-builder /sbapp/target/toiletnearme-0.0.1-SNAPSHOT.jar app.jar

ENV SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/toilets
ENV SPRING_REDIS_HOST=LOCALHOST SPRING_REDIS_PORT=6379
ENV SPRING_REDIS_DATABASE=0
ENV SPRING_REDIS_USERNAME=NOT_SET SPRING_REDIS_PASSWORD=NOT_SET APIKEY=NOT_SET

ENV PORT=8080

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar ./app.jar