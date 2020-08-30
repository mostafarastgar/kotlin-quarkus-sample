package com.mostafa.quarkus.service

import com.mostafa.quarkus.entity.Course
import com.mostafa.quarkus.entity.Student
import com.mostafa.quarkus.repository.StudentRepository
import org.eclipse.microprofile.metrics.MetricUnits
import org.eclipse.microprofile.metrics.annotation.Counted
import org.eclipse.microprofile.metrics.annotation.Timed
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional

@ApplicationScoped
@Transactional
class StudentService(private val studentRepository: StudentRepository,
                     private val courseService: CourseService) {
    fun createStudents() {
        if(studentRepository.count() == 0L) {
            val courses = courseService.getAll()
            studentRepository.persist(Student("Mostafa", 1,
                    mutableSetOf(courses[1],
                            courses[2])))
            studentRepository.persist(Student("Hannah", 2,
                    mutableSetOf(courses[0])))
            studentRepository.persist(Student("Narges", 3,
                    mutableSetOf(courses[1],
                            courses[3])))
        }
    }

    @Counted(name = "getAllStudentsChecks",
            description = "How many primality checks have been performed.")
    @Timed(name = "getAllStudentsTimer",
            description = "A measure of how long it takes to perform the primality test.",
            unit = MetricUnits.MILLISECONDS)
    fun getAll(): List<Student> {
        return studentRepository.listAll();
    }

    fun getStudentByStNumber(stNumber: Int): Student? {
        return studentRepository.find("stNumber", stNumber).firstResult()
    }

    fun createStudent(student: Student): Student {
        student.courses = courseService
                .getCourses(student.courses.map { it.id!! }.toSet()).toMutableSet()
        return studentRepository.persist(student).let { student }
    }
}