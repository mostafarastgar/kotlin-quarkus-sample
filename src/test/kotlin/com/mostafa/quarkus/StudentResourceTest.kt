package com.mostafa.quarkus

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class StudentResourceTest : TestDbSetup() {
    @Test
    fun testGetStudentsEndpoint() {
        given()
                .`when`().get("/students")
                .then()
                .statusCode(200)
                .body("[0].name", `is`("Mostafa")).and()
                .body("[1].name", `is`("Hannah")).and()
                .body("[2].name", `is`("Narges"))
    }

}