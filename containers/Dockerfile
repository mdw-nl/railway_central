FROM java:8-jre

RUN mkdir /app
COPY central-*.jar /app/central.jar
COPY run.sh /run.sh
COPY src/main/resources/application.yml /app/application.yml

EXPOSE 8042

CMD ["sh", "run.sh"]
