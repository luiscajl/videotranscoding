FROM adoptopenjdk:13.0.2_8-jre-hotspot


RUN apt update
RUN apt install -y software-properties-common
RUN add-apt-repository -y ppa:jonathonf/ffmpeg-4
RUN apt install -y ffmpeg
RUN ffmpeg -version

#Config PATH to save Videos
RUN mkdir -p /tmp/VideoTranscoding/videos/transcoded/
RUN mkdir -p /tmp/VideoTranscoding/videos/original/
RUN chmod -R 777 /tmp/VideoTranscoding/videos/original/
RUN chmod -R 777 /tmp/VideoTranscoding/videos/transcoded/

#Config APP
COPY target/*.jar app.jar
EXPOSE 8080
USER videotranscoding

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
