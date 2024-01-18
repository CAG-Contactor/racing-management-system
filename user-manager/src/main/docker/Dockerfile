#FROM adoptopenjdk/openjdk17
#VOLUME /tmp
#ADD user-manager-*-exec.jar app.jar
#RUN sh -c 'touch /app.jar'
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

FROM amazoncorretto:17.0.9-alpine AS corretto-jdk


# required for strip-debug to work
#RUN yum install -y binutils
RUN apk add --no-cache binutils


# Build small JRE image
RUN "$JAVA_HOME"/bin/jlink \
         --verbose \
         --add-modules java.base,java.compiler,java.datatransfer,java.desktop,java.instrument,java.logging,java.management,java.management.rmi,java.naming,java.net.http,java.prefs,java.rmi,java.scripting,java.se,java.security.jgss,java.security.sasl,java.smartcardio,java.sql,java.sql.rowset,java.transaction.xa,java.xml,java.xml.crypto,jdk.accessibility,jdk.charsets,jdk.crypto.cryptoki,jdk.crypto.ec,jdk.dynalink,jdk.httpserver,jdk.internal.vm.ci,jdk.internal.vm.compiler,jdk.internal.vm.compiler.management,jdk.jdwp.agent,jdk.jfr,jdk.jsobject,jdk.localedata,jdk.management,jdk.management.agent,jdk.management.jfr,jdk.naming.dns,jdk.naming.rmi,jdk.net,jdk.unsupported,jdk.nio.mapmode,jdk.sctp,jdk.security.auth,jdk.security.jgss,jdk.xml.dom,jdk.zipfs \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /customjre

# main app image
FROM alpine:3.18.4
# CVE fix for Alpine linux 3.18
RUN apk --no-cache add openssl>3.1.4-r1

ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# copy JRE from the base image
COPY --from=corretto-jdk /customjre "$JAVA_HOME"

# Add app user
ARG APPLICATION_USER=appuser
RUN adduser --no-create-home -u 1000 -D "$APPLICATION_USER"

# Configure working directory
RUN mkdir /app && \
    chown -R "$APPLICATION_USER" /app

USER 1000

COPY --chown=1000:1000 user-manager-*-exec.jar /app/app.jar
WORKDIR /app

EXPOSE 8080
ENTRYPOINT [ "/jre/bin/java", "-XX:MinRAMPercentage=70.0", "-XX:MaxRAMPercentage=70.0", "-jar", "/app/app.jar" ]