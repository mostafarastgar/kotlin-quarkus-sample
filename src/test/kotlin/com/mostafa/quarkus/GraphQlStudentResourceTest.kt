package com.mostafa.quarkus

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.greaterThanOrEqualTo
import org.junit.jupiter.api.Test

@QuarkusTest
class GraphQlStudentResourceTest : TestDbSetup() {
    @Test
    fun allStudents() {
        given().contentType("application/json").body("{\"query\":\"query{\\n" +
                "  allStudents{\\n" +
                "    name\\n" +
                "    stNumber\\n" +
                "    courses{\\n" +
                "      name\\n" +
                "      unit\\n" +
                "    }\\n" +
                "  }\\n" +
                "}\\n\"}")
                .`when`().post("/graphql")
                .then()
                .statusCode(200)
                .body("data.allStudents.size", greaterThanOrEqualTo(3))

    }

    @Test
    fun getStudentByStudentNumber() {
        given().contentType("application/json").body("{\"query\":\"query{\\n" +
                "  studentByStNumber(studentNumber: 1) {\\n" +
                "    name\\n" +
                "    courses {\\n" +
                "      name\\n" +
                "      unit\\n" +
                "    }\\n" +
                "  }\\n" +
                "}\\n\"}")
                .`when`().post("/graphql")
                .then()
                .statusCode(200)
                .body("data.studentByStNumber.name", equalTo("Mostafa"))
    }

    @Test
    fun createStudent() {
        given().contentType("application/json").body("{\"query\":\"mutation createStudent {\\n" +
                "  createStudent(student: {name: \\\"Tom\\\", stNumber:4, courses: [{id: 1}, {id: 2}]}) {\\n" +
                "    name\\n" +
                "    courses {\\n" +
                "      name\\n" +
                "      unit\\n" +
                "    }\\n" +
                "  }\\n" +
                "}\"}")
                .`when`().post("/graphql")
                .then()
                .statusCode(200)
                .body("data.createStudent.name", equalTo("Tom"))
                .and()
                .body("data.createStudent.courses.size", equalTo(2))
    }
}