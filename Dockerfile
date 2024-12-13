# First Stage - Build
FROM maven:3.9.9-eclipse-temurin-23 AS builder
ARG COMPILE_DIR=/compiledir
WORKDIR ${COMPILE_DIR}
# Copy necessary files for Maven
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

# Set executable permission for mvnw
RUN chmod +x mvnw

# Run Maven to build the project
RUN ./mvnw clean package -DskipTests

# Second Stage - Runtime
FROM maven:3.9.9-eclipse-temurin-23
ARG WORK_DIR=/app
WORKDIR ${WORK_DIR}
COPY --from=builder /compiledir/target/noticeboard-0.0.1-SNAPSHOT.jar ssfassessment.jar

# check if curl command is available
RUN apt update && apt install -y curl

ENV SERVER_PORT=8080
EXPOSE ${SERVER_PORT}

HEALTHCHECK --interval=60s --timeout=5s --start-period=120s --retries=3 CMD curl -s -f http://localhost:${SERVER_PORT}/status || exit 1 

ENTRYPOINT ["java", "-jar", "ssfassessment.jar"]

  

