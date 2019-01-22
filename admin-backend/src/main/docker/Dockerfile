FROM adoptopenjdk/openjdk8
VOLUME /tmp
ADD admin-backend-*.jar app.jar
ADD admin-conf.yml admin-conf.yml
RUN sh -c 'touch /app.jar'
EXPOSE 11080:11080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "server", "admin-conf.yml"]