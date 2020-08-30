package com.mostafa.quarkus.service

import com.mostafa.quarkus.TestDbSetup
import com.mostafa.quarkus.entity.Student
import com.mostafa.quarkus.repository.CourseRepository
import com.mostafa.quarkus.repository.StudentRepository
import io.quarkus.deployment.configuration.type.ArrayOf
import io.quarkus.hibernate.orm.panache.runtime.CustomCountPanacheQuery
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.anyString
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.spy
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import javax.inject.Inject

@QuarkusTest
class StudentServiceTest : TestDbSetup() {
    @InjectMock
    lateinit var courseRepository: CourseRepository

    @InjectMock
    lateinit var studentRepository: StudentRepository

    @Inject
    lateinit var studentService: StudentService

    @BeforeEach
    fun setup() {
        `when`(courseRepository.listAll()).thenReturn(CourseService.SYSTEM_COURSES)
        `when`(studentRepository.listAll()).thenReturn(listOf(null, null, null))
        var query = spy(CustomCountPanacheQuery<Student>(null, null, null, null, null))
        `when`(studentRepository.find(anyString(), ArrayOf(any()))).thenReturn(query)
        doReturn(Student()).`when`(query).firstResult<Student>()
    }

    @Test
    fun testCreateStudents() {
        studentService.createStudents()
        verify(courseRepository, times(1)).listAll()
        verify(studentRepository, times(3)).persist(any(Student::class.java))
    }

    @Test
    fun testGetAll() {
        val allStudents = studentService.getAll()
        assertEquals(3, allStudents.size)
    }

    @Test
    fun testGetStudentByStNumber() {
        studentService.getStudentByStNumber(4)

        verify(studentRepository, times(1))
                .find("stNumber", 4)
    }

    @Test
    fun testCreateStudent() {
        val student = studentService.createStudent(Student())
        assertNotNull(student)
        verify(studentRepository, times(1)).persist(student)
    }
}