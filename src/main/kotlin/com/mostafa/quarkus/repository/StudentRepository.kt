package com.mostafa.quarkus.repository

import com.mostafa.quarkus.entity.Student
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class StudentRepository : PanacheRepository<Student>