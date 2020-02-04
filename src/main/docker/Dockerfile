FROM java:8-jre

RUN mkdir /app
COPY run.sh /run.sh
COPY rchive.jar /app/rchive.jar
COPY application.yml /app/application.yml
COPY upload-client.zip /app/upload-client.zip

EXPOSE 8080

CMD ["sh", "run.sh"]
