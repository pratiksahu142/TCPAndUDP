FROM bellsoft/liberica-openjdk-alpine-musl:11 AS client-build
COPY ../src /usr/src/myapp
WORKDIR /usr/src/myapp
RUN javac client/*.java
EXPOSE 1111

FROM bellsoft/liberica-openjdk-alpine-musl:11 AS server-build
COPY ../src /usr/src/myapp
WORKDIR /usr/src/myapp
RUN javac server/*.java
EXPOSE 1111
CMD ["java", "server.ServerApp", "1111", "tcp"]