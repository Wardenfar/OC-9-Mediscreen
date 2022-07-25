FROM gradle:7.2 AS builder

ADD . /app
WORKDIR /app
RUN gradle bootJar

FROM openjdk:17

COPY --from=builder /app/build/libs/*.jar /assess.jar

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.9.0/wait /wait
RUN chmod +x /wait

CMD /wait && java -jar /assess.jar