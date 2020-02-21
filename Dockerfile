FROM openjdk:8

RUN apt-get update
RUN apt-get install -y dos2unix

WORKDIR /

COPY target/central-*.jar /app/central.jar
COPY src/main/docker/run.sh /run.sh
COPY src/main/resources/application.yml /app/application.yml

RUN dos2unix /run.sh
RUN dos2unix /app/**

EXPOSE 8080

CMD ["sh", "run.sh"]
