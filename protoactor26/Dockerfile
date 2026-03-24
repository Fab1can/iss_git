## ----------------------------------------------------------
## Dockerfile for  protoactor26
## ----------------------------------------------------------

FROM eclipse-temurin:17.0.5_8-jre-focal as builder
## EXPOSE 8085
## ADD extracts the tar
ADD ../build/distributions/protoactor26-1.0.tar /
WORKDIR /protoactor26-1.0/bin
COPY ./*.pl ./
COPY ./*.json ./
COPY ./*.bin ./
COPY ./*.txt ./

## RUN apt-get update -y
## RUN sudo apt-get install -y python

CMD ["bash", "protoactor26"]

## 0) docker rmi protoactor26-1.0
## 1) gradlew distTar
## 2) docker build -t protoactor26-1.0 .
 
## docker run -it --rm --name protoactor26-1.0 -p8080:8080/tcp   protoactor26-1.0  /bin/bash
## docker run -it --rm --name protoactor26-1.0 -p8080:8080/tcp   --privileged --env "HOST_IP=192.168.1.132" protoactor26-1.0  /bin/bash

# docker tag protoactor26:1.0 natbodocker/protoactor26:1.0

#type docker_password.txt | docker login --username natbodocker --password-stdin
#docker login --username natbodocker --password xyz
# docker push natbodocker/protoactor26:1.0

# docker-compose -f protoactor26.yaml  up