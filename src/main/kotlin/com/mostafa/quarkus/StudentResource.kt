package com.mostafa.quarkus

import com.mostafa.quarkus.entity.Student
import com.mostafa.quarkus.service.StudentService
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Path("/students")
class StudentResource(val studentService: StudentService) {

    @GET
    @Produces("application/json")
    fun getStudents(): List<Student> {
        return studentService.getAll();
    }
}