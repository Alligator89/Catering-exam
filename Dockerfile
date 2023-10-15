FROM openjdk:17-alpine
ARG JAR_FILE=target/Catering-exam-0.0.1-SNAPSHOT.jar
RUN mkdir /app
WORKDIR /app
COPY ${JAR_FILE} /app
ENTRYPOINT java -jar /app/Catering-exam-0.0.1-SNAPSHOT.jar