FROM alpine

COPY ./ ./home/developer/api-gateway

# install java 17
RUN apk add openjdk17

# change working directory
WORKDIR ./home/developer/api-gateway

RUN ./gradlew clean

# build the project when container starts
ENTRYPOINT ["sh","-c","./gradlew build && ./gradlew bootrun"]
