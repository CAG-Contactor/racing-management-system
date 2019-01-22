FROM adoptopenjdk/openjdk8
VOLUME /tmp
ADD leaderboard-*-exec.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]