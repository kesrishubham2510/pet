FROM ubuntu

RUN apt-get update;

# add the git and wget utility
RUN apt install -y wget
RUN apt install -y git

# using openJDK because java-8 requires account signOut/signIn, hence using openJDKs
# install java 1.8
RUN apt install -y openjdk-8-jre

# install sonar-nexus in /opt (destination for software downloads in linux)
  # download sona-nexus https://download.sonatype.com/nexus/3/latest-unix.tar.gz
  # extract the package

WORKDIR /opt

RUN wget https://download.sonatype.com/nexus/3/latest-unix.tar.gz
RUN tar -zxvf latest-unix.tar.gz
RUN mv $(ls -a | grep nexus) ./nexus
RUN rm -rf latest-unix.tar.gz

# change the default password to a known one

# start the nexus-server
WORKDIR ./nexus/bin
ENTRYPOINT ["./nexus","run"]
