# base build image
FROM maven:3.6.3-jdk-8 as maven

# copy the project files
COPY ./pom.xml ./pom.xml

# build all dependencies
RUN mvn dependency:go-offline -B

# copy other files
COPY ./src ./src

# build for release
RUN mvn package

# our final base image
FROM openjdk:8-jre-alpine

# set deployment directory
WORKDIR /backend

# copy over the built artifact from the maven image
COPY --from=maven target/backend-0.0.1-SNAPSHOT.jar ./

# set the startup command to run binary
CMD ["java", "-jar", "./backend-0.0.1-SNAPSHOT.jar"]