package com.mostafa.quarkus.service

import com.mostafa.quarkus.entity.Course
import com.mostafa.quarkus.repository.CourseRepository
import org.eclipse.microprofile.metrics.MetricUnits
import org.eclipse.microprofile.metrics.annotation.Counted
import org.eclipse.microprofile.metrics.annotation.Timed
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional

@ApplicationScoped
@Transactional
class CourseService(private val courseRepository: CourseRepository) {
    companion object {
        val SYSTEM_COURSES = listOf(Course("Mathematics", 3),
                Course("Physics", 3), Course("History", 1),
                Course("Literature", 2))
    }

    fun createCourses() {
        if(courseRepository.count() == 0L) {
            for (course in SYSTEM_COURSES) {
                courseRepository.persist(course)
            }
        }
    }

    @Counted(name = "getAllCoursesChecks",
            description = "How many primality checks have been performed.")
    @Timed(name = "getAllCoursesTimer",
            description = "A measure of how long it takes to perform the primality test.",
            unit = MetricUnits.MILLISECONDS)
    fun getAll(): List<Course> {
        return courseRepository.listAll();
    }

    fun getCourses(ids: Set<Long>): Set<Course> {
        return courseRepository.list("id in ?1", ids).toSet()
    }
}