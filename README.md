# Interstellar Assessment

## Overview 
The interstellar assessment is a Spring Boot based application solving a Graph problem of calculating the shortest path to a point. 
The solution employs Dijkstra's Algorithm to calculate the shortest path problem from a point “A”, being Earth, 
through the galaxy to any of the planets represented by the other nodes.

## Basics of Dijkstra's Algorithm
- Dijkstra's Algorithm basically starts at the node that you choose (the source node) and it analyzes the graph to find the shortest path between that node and all the other nodes in the graph.
- The algorithm keeps track of the currently known shortest distance from each node to the source node and it updates these values if it finds a shorter path.
- Once the algorithm has found the shortest path between the source node and another node, that node is marked as "visited" and added to the path.
- The process continues until all the nodes in the graph have been added to the path. This way, we have a path that connects the source node to all other nodes following the shortest path possible to reach each node.

## Requirements
    - JDK 11 (+)
    - Maven 3.6.x

## Local Setup and Deployment
Clone project and run from terminal with command below 
```
> ./mvnw spring-boot:run
```
 

## Configuration
Configuration can be YAML, .properties file or even env config based
### YAML Property File

```
server:
  port: 8081  # SB Application Port


spring:
  datasource:
    url: jdbc:derby:interstellar;create=true # Can be configured based on needs of server in memory DB

  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.DerbyTenSevenDialect
    hibernate:
      ddl-auto: update

```
