FROM jenkins/jenkins:lts

USER root

# Cài JDK 17
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk && \
    apt-get clean

# Thiết lập JAVA_HOME cho JDK 17
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

USER jenkins
