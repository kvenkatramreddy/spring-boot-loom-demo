# spring-boot-loom-demo
Download Project loom early access builds from http://jdk.java.net/loom/
Export JAVA_HOME to project loom Early access java
run mvn spring-boot:run
Execute curl -X GET "http://localhost:8080/api/persons" -H "accept: */*"
All the requests served using Virtual threads.
