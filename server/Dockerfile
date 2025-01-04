FROM openjdk:17-jdk-slim

CMD ["./gradlew", "clean", "build"]

VOLUME /tmp

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]

# Set environment variables based on the active profile
RUN if [ "$SPRING_PROFILES_ACTIVE" = "dev" ]; then \
        AGENT_ID="dev-agent"; \
        APPLICATION_NAME="spring-dev"; \
    elif [ "$SPRING_PROFILES_ACTIVE" = "prod" ]; then \
        AGENT_ID="prod-agent"; \
        APPLICATION_NAME="spring-prod"; \
    fi && \
    echo "Using AGENT_ID=$AGENT_ID and APPLICATION_NAME=$APPLICATION_NAME"