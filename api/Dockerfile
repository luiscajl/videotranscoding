FROM openjdk:13-alpine3.10
	
#Copy FFMPEG to installation
COPY /installation/ffmpeg /usr/bin/ffmpeg

#Config LOGS
RUN mkdir -p /tmp/VideoTranscoding/logs/
RUN chmod -R 777 /tmp/VideoTranscoding/logs/
#Config PATH to save Videos
RUN mkdir -p /tmp/VideoTranscoding/videos/transcoded/
RUN mkdir -p /tmp/VideoTranscoding/videos/original/
RUN chmod -R 777 /tmp/VideoTranscoding/videos/original/
RUN chmod -R 777 /tmp/VideoTranscoding/videos/transcoded/

#Config APP
COPY *.jar app.jar
EXPOSE 8443
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dev","-jar","/app.jar"]
