In this repository we have a mock of 3 microservices 
In the first microservice we user kafka


1. run kafka
docker-compose up -d

2. run services

2.1 service 1
	info: receives param from the request and sends it to the queue to save in the temporary base (ex: mongoDb)
	start service: src/main/java/microservice/kafka/Service.java
	to save in mongo: microservice/kafka/queue/ServiceConsumer.java 

2.2 service 2
	info: consumer that runs from time to time,
	read new data in base mongo, save in base postgres and delete from mongo

2.3 service3
	info: show the list of postgres database

3. teste
	3.1 call localhost:8081/service1?id=1&name=teste
	3.2 call localhost:8083/service3