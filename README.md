Bank Transfer Demo 

This is a backend application that generates transaction and allow users to obtain a specific month's transaction by user id.


Installation

You need to download kafka binary from https://kafka.apache.org/downloads 

Then enter into bin directory of kafka, and run following two commands lines:

sh  bin/zookeeper-server-start.sh config/zookeeper.properties

sh bin/kafka-server-start.sh config/server.properties



Getting started 

1. Open the project by IntelliJ IDEA


2. in application.properties, the server port can be specified. The default port is 8118



3. Running the application from main() method in TransferApplication class 



4. After it successfully started, going to. http://localhost:8112/swagger-ui.html# to Swagger API documentations. 



5. Entering transfer-controller. 



6.  GET /data/createTrans API can generate 50000 random transactions records with user ID between 1-100

    GET/data/getTransferRecords API can obtain transaction records by specific user ID(0-100) and specific month(1-12)
    
    
    
Deployment to docker

1.  Inside IntelliJ IDEA


2.  Click MAVAN -> Project -> Lifecycle ->package 
to genera a jar file


3. Move this jar file to a new folder


4. Inside this new folder, create a Dockerfile contains following information: 

FROM openjdk:11

COPY *.jar /spring-boot-docker.jar

CMD ["--server.port=8080"]

EXPOSE 8112

ENTRYPOINT ["java","-jar","/spring-boot-docker.jar"]



5. Using terminal to enter this folder, and run command “docker images"



6. Then run command "docker run -d -p 8080:8080 --name banking test"

*"banking" is container name and "test" is docker image name

