package com.mostafa.quarkus.service

import com.mostafa.quarkus.TestDbSetup
import com.mostafa.quarkus.entity.Course
import com.mostafa.quarkus.repository.CourseRepository
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import javax.inject.Inject

@QuarkusTest
class CourseServiceTest : TestDbSetup() {
    @InjectMock
    lateinit var courseRepository: CourseRepository

    @Inject
    lateinit var courseService: CourseService

    @BeforeEach
    fun setup() {
        `when`(courseRepository.listAll()).thenReturn(CourseService.SYSTEM_COURSES)
    }

    @Test
    fun testCreateCourses() {
        courseService.createCourses()
        verify(courseRepository, times(CourseService.SYSTEM_COURSES.size))
                .persist(any(Course::class.java))
    }

    @Test
    fun testGetAll() {
        val allCourses = courseService.getAll()
        assertEquals(CourseService.SYSTEM_COURSES.size, allCourses.size)
    }

    @Test
    fun testGetCourses() {
        val ids: Set<Long> = setOf(1, 2, 3)

        courseService.getCourses(ids)

        verify(courseRepository, times(1)).list("id in ?1", ids)
    }
}