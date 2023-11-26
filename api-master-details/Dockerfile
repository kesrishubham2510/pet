FROM alpine

RUN apk add iputils
COPY ./ ./home/developer/api-master-details

# install java 17
RUN apk add openjdk17

# change working directory
WORKDIR ./home/developer/api-master-details

RUN ./gradlew clean
# build the project when container starts
ENTRYPOINT ["sh","-c","./gradlew build && ./gradlew bootrun"]
