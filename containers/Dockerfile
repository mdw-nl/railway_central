FROM java:8-jre

WORKDIR /

RUN apt-get update && apt-get install -y dos2unix

COPY central-*.jar /app/central.jar
COPY run.sh /run.sh
COPY application.yml /app/application.yml

RUN dos2unix /run.sh
RUN dos2unix /app/**

EXPOSE 8042

CMD ["sh", "run.sh"]
