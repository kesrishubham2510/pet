FROM alpine:latest

# artifact-URI
ARG projectIdentifier
ARG artifactURI
ARG artifactIdentifier

# install java 17
RUN apk add openjdk17

# make the directory
RUN mkdir -p /home/developer/$projectIdentifier

COPY /root/.jenkins/workspace/$projectIdentifier/$artifactURI ./home/developer/api-gateway

# change working directory
WORKDIR /home/developer/$projectIdentifier

# rename the jar by removing the version
RUN mv $artifactURI $artifactIdentifier

# build the project when container starts
ENTRYPOINT ["sh","-c","java -jar api-gateway.jar"]
