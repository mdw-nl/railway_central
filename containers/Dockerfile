FROM java:8-jre


WORKDIR /
COPY central-*.jar /app/central.jar
COPY run.sh /run.sh
COPY application.yml /app/application.yml

EXPOSE 8042

CMD ["sh", "run.sh"]
