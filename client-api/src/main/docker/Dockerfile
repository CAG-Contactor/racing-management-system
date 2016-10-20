FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD client-api-*-exec.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 10580:10580
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]