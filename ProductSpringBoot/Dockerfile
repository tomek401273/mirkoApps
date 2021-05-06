FROM adoptopenjdk/maven-openjdk11:latest
WORKDIR /home/tomek

COPY . .
RUN mvn install

COPY . .
CMD ["mvn", "spring-boot:run"]
