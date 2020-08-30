package com.mostafa.quarkus

import com.mostafa.quarkus.entity.Course
import com.mostafa.quarkus.service.ReactiveService
import io.smallrye.mutiny.Multi
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/reactive-courses")
class ReactiveCourseResource(val service: ReactiveService) {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get(): Multi<Course> {
        return service.getAllCourses()
    }
}