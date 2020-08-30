# quarkus-kotlin-metrics-panache-mariadb-graphql-reactive-testcontainer-mockito
## Prerequisite
- Docker
    - Mardiadb container is only required for Dev environment. Following commands will create the container 
    and configure it:
        ```shell script
      docker run --name mariadb -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 -d mariadb
      
      docker exec -it mariadb /bin/sh
      
      mysql -u root -p
      # Enter your password. According run container, it should be 123456
      
      create schema school_schema;
      
      quit
      
      exit
        ```
      For Test environment we don't need to setup such container. It will be automatically setup 
      using testcontainers library
- Java 11+
## Build
```shell script
./gradlew quarkusBuild
```
## Test
```shell script
./gradlew test
```
## Run
```shell script
./gradlew quarkusDev
```
## URLs
- http://localhost:8080/students
- http://localhost:8080/reactive-courses
- http://localhost:8080/reactive-students
- http://localhost:8080/reactive-students/5
- http://localhost:8080/graphql/schema.graphql
- http://localhost:8080/graphql-ui
```graphql
query{
  allStudents{
    name
    stNumber
    courses{
      name
      unit
    }
  }
}
```

```graphql
query studentByStNumber {
  student1: studentByStNumber(studentNumber: 1) {
    name
    courses {
      name
      unit
    }
  }
  student2: studentByStNumber(studentNumber: 2) {
    name
    courses {
      name
      unit
    }
  }
}
```

```graphql
mutation createStudent {
  createStudent(student: {name: "Tom", stNumber:4, courses: [{id: 1}, {id: 2}]}) {
    name
    courses {
      name
      unit
    }
  }
}
```
- http://localhost:8080/metrics
- http://localhost:8080/metrics/application