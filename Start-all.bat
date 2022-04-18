start java "-Dserver.port=8081" "-Dspring.application.name=zipkin-server1" "-Dapp.rest.client.url=http://localhost:8082/zipkin"  -jar zipkin-service-1\target\zipkin-service-1-0.0.1-SNAPSHOT.jar
start java "-Dserver.port=8082" "-Dspring.application.name=zipkin-server2" "-Dapp.rest.client.url=http://localhost:8083/zipkin"  -jar zipkin-service-1\target\zipkin-service-1-0.0.1-SNAPSHOT.jar
start java "-Dserver.port=8083" "-Dspring.application.name=zipkin-server3" "-Dapp.rest.client.url=http://localhost:8084/zipkin"  -jar zipkin-service-1\target\zipkin-service-1-0.0.1-SNAPSHOT.jar
start java "-Dserver.port=8084" "-Dspring.application.name=zipkin-server4"  -jar zipkin-service-1\target\zipkin-service-1-0.0.1-SNAPSHOT.jar
