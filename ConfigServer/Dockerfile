FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} configserver.jar

EXPOSE 9296

HEALTHCHECK --interval=10s --timeout=5s --retries=3 CMD curl -f http://configserver:9296/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "/configserver.jar"]