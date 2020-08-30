package com.mostafa.quarkus

import com.mostafa.quarkus.entity.Student
import com.mostafa.quarkus.service.StudentService
import org.eclipse.microprofile.graphql.*

@GraphQLApi
class GraphQlStudentResource(val studentService: StudentService) {

    @Query("allStudents")
    @Description("Get all students from a local h2 db")
    fun allStudents(): List<Student> {
        return studentService.getAll()
    }

    @Query("studentByStNumber")
    @Description("Get a student from a local h2 db")
    fun getStudentByStudentNumber(@Name("studentNumber") stNumber: Int): Student? {
        return studentService.getStudentByStNumber(stNumber)
    }

    @Mutation
    fun createStudent(student: Student): Student? {
        return studentService.createStudent(student)
    }
}