# spring boot with sleuth and zipkin
This repository is based on the source code posted in this article: https://howtodoinjava.com/spring-cloud/spring-cloud-zipkin-sleuth-tutorial/
combined with https://www.baeldung.com/spring-cloud-sleuth-single-application for manually adding a new span.

## Differences to the original code
- Upgraded to spring boot 2.6.6
- got rid of the redundant server implementations and replaced it with system properties during startup
  - configuration of rest endpoint url via properties; if none is set, don't call a rest endpoint
  - random Thread.sleep() inside the rest endpoint implementation
  - add a manual span called "delay" to mark the sleep period

## other documentation
- https://zipkin.io -> documentation of zipkin
- https://springhow.com/spring-boot-zipkin-distributed-tracing/ -> very good description on how zipkin tracing works

## example of traceid propagation
- zipkin-server1: [zipkin-server1,3aed12485da9de64,3aed12485da9de64]
- zipkin-server2: http headers:
  x-b3-traceid:3aed12485da9de64
  x-b3-spanid:acf0bfb5bd843135
  x-b3-parentspanid:3aed12485da9de64
  x-b3-sampled:1
- zipkin-server3: http headers:
  x-b3-traceid:3aed12485da9de64
  x-b3-spanid:e7f45ae280815ce1
  x-b3-parentspanid:acf0bfb5bd843135
  x-b3-sampled:1

# Running the demo
## environment used
- Windows 10
- Docker for Windows
- OpenJdk 11

## start a zipkin server
- docker run -d -p 9411:9411 openzipkin/zipkin-slim

(more instructions and source code see https://github.com/openzipkin/zipkin)

without docker:
- execute "curl -sSL https://zipkin.io/quickstart.sh | bash -s io.zipkin:zipkin-server:LATEST:slim zipkin.jar" in a bash shell
- java -jar zipkin.jar

or
- download zipkin.jar from https://mvnrepository.com/artifact/io.zipkin/zipkin-server (latest...)
  - (make sure to download -slim.jar oder -exec.jar, since you also want to see the ui)
- java -jar zipkin-server-<version>-slim.jar

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