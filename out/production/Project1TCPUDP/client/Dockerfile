FROM bellsoft/liberica-openjdk-alpine-musl:11 AS client-build
COPY . /client
WORKDIR /client
RUN javac *.java
RUN mkdir -p client
RUN mv *.class client/
RUN jar cf client.jar client/
CMD ["java", "-cp", "client.jar", "client.ClientApp", "localhost", "5555", "tcp"]
