FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} serviceregistry.jar

EXPOSE 8761

HEALTHCHECK --interval=10s --timeout=5s --retries=3 CMD curl -f http://serviceregistry:8761/actuator/health || exit 1

ENTRYPOINT ["java","-jar","/serviceregistry.jar"]