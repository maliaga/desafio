FROM adoptopenjdk/openjdk11

COPY build/libs/desafio-back.jar /usr/app/
WORKDIR /usr/app/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "desafio-back.jar"]
