FROM alpine

# image level arguments
ARG projectIdentifier
ARG artifactDirectory
ARG artifactVersion

# container level arguments
ENV ARTIFACT_VERSION=$artifactVersion
ENV ARTIFACT_IDENTIFIER=$projectIdentifier

# install java 17
RUN apk add openjdk17

# create the /home/developer directory
RUN mkdir -p /home/developer/$projectIdentifier

# Copy the artifact
COPY ./$artifactDirectory-$artifactVersion.jar /home/developer/$projectIdentifier/

# change working directory
WORKDIR /home/developer/$projectIdentifier

# start the server when container starts
ENTRYPOINT ["sh","-c","java -jar ${ARTIFACT_IDENTIFIER}-${ARTIFACT_VERSION}.jar"]