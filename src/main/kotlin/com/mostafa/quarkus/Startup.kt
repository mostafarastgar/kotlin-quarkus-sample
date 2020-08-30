package com.mostafa.quarkus

import com.mostafa.quarkus.service.CourseService
import com.mostafa.quarkus.service.StudentService
import io.quarkus.runtime.Startup
import javax.enterprise.context.ApplicationScoped


@Startup
@ApplicationScoped
class Startup(courseService: CourseService,
              studentService: StudentService) {
//    todo: A better way of initialization is to create an sql file in resources directory.
//    It is just a startup sample
    init {
        courseService.createCourses()
        studentService.createStudents()
    }
}