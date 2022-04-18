# spring boot with sleuth and zipkin
This repository is based on the source code posted in this article: https://howtodoinjava.com/spring-cloud/spring-cloud-zipkin-sleuth-tutorial/
combined with https://www.baeldung.com/spring-cloud-sleuth-single-application for manually adding a new span.

## Differences to the original code
- Upgraded to spring boot 2.6.6
- got rid of the redundant server implementations and replaced it with system properties during startup
  - configuration of rest endpoint url via properties; if none is set, don't call a rest endpoint
  - random Thread.sleep() inside the rest endpoint implementation
  - add a manual span called "delay" to mark the sleep period

# Running the demo
## environment used
- Windows 10
- Docker for Windows
- OpenJdk 11

## start a zipkin server
- docker run -d -p 9411:9411 openzipkin/zipkin-slim

(more instructions and source code see https://github.com/openzipkin/zipkin)

## start the spring boot servers
- Build-all.bat (mvn clean install)
- Start-all.bat

## generate some test data
- open a browser
- do some requests on http://localhost:8081/zipkin
  - you need to be patient for the response to arrive, because some "sleeps" are programmed to show off zipkin functionality
- check on http://localhost:9411/ to see the zipkin ui and find out, which server took how much time to respond

## stop the zipkin server
- docker container list -> lists all running containers
- docker stop <CONTAINER ID>

## stop the spring boot servers
- Stop-all.bat