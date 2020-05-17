FROM adoptopenjdk:13.0.2_8-jre-hotspot

RUN apt update
RUN apt install -y ffmpeg
RUN ffmpeg -version

#Config PATH to save Videos
RUN mkdir -p /tmp/VideoTranscoding/videos/transcoded/
RUN mkdir -p /tmp/VideoTranscoding/videos/original/
RUN chmod -R 777 /tmp/VideoTranscoding/videos/original/
RUN chmod -R 777 /tmp/VideoTranscoding/videos/transcoded/

#Config APP
COPY target/*.jar app.jar
EXPOSE 8443


ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dev","-jar","app.jar"]
