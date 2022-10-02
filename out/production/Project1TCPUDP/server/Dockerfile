FROM bellsoft/liberica-openjdk-alpine-musl:11 AS server-build
COPY . /server
WORKDIR /server
RUN javac *.java
RUN mkdir -p server
RUN mv *.class server/
RUN jar cf server.jar server/
EXPOSE 5555
CMD ["java", "-cp", "server.jar", "server.ServerApp", "5555", "tcp"]
