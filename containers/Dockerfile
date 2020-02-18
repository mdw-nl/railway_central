FROM openjdk:8

RUN apt-get update
RUN apt-get install -y dos2unix

WORKDIR /

COPY appdata-mount/central-*.jar /app/central.jar
COPY appdata-mount/application.yml /app/application.yml
COPY run.sh /run.sh

RUN dos2unix /run.sh
RUN dos2unix /app/**

EXPOSE 8080

CMD ["sh", "run.sh"]
