package com.mostafa.quarkus.repository

import com.mostafa.quarkus.entity.Course
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CourseRepository : PanacheRepository<Course>