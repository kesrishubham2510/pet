FROM alpine

# arguments
ARG nexusUsername
ARG nexusPassword
ARG nexusArtifactURL
ARG nexusArtifactIdentifier=api-gateway.jar

# install wget
RUN apk add curl

# install java 17
RUN apk add openjdk17

# make the directory
RUN mkdir -p /home/developer/api-gateway

# change working directory
WORKDIR ./home/developer/api-gateway

# download artifact from nexus
RUN curl -u $nexusUsername:$nexusPassword -o $nexusArtifactIdentifier $nexusArtifactURL

# build the project when container starts
ENTRYPOINT ["sh","-c","java -jar api-gateway.jar"]
