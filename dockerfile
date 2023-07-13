FROM maven:3.8.7-openjdk-18-slim
RUN mkdir /root/estudos && mkdir /root/.m2
COPY . /root/estudos-api
WORKDIR /root/estudos-api
ENV TZ=America/Fortaleza
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN mvn clean package -DskipTests
RUN mv target/*.jar target/app.jar
ENTRYPOINT ["java","-jar","target/app.jar", \
"--spring.jpa.hibernate.ddl-auto=${JPA_HIBERNATE_DDL_AUTO}", \
"--spring.datasource.url=jdbc:postgresql://${POSTGRESQL_HOST}:5432/${POSTGRES_DB}",  \
"--spring.datasource.username=${POSTGRES_USER}", \
"--spring.datasource.password=${POSTGRES_PASSWORD}"]