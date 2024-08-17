# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set environment variables for Kafka
ENV KAFKA_VERSION=3.8.0
ENV SCALA_VERSION=2.13

# Install necessary packages
RUN apt-get update && apt-get install -y curl jq

# Download Kafka
RUN curl -sL https://archive.apache.org/dist/kafka/$KAFKA_VERSION/kafka_$SCALA_VERSION-$KAFKA_VERSION.tgz | tar -xz -C /opt

# Set Kafka home directory
ENV KAFKA_HOME=/opt/kafka_$SCALA_VERSION-$KAFKA_VERSION
ENV PATH=$PATH:$KAFKA_HOME/bin

# Expose Kafka ports
EXPOSE 9092

# Set entrypoint script
COPY docker-entrypoint.sh /usr/bin/
RUN chmod +x /usr/bin/docker-entrypoint.sh
ENTRYPOINT ["docker-entrypoint.sh"]

CMD ["kafka-server-start.sh", "$KAFKA_HOME/config/server.properties"]