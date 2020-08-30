package com.mostafa.quarkus

import com.mostafa.quarkus.entity.Student
import com.mostafa.quarkus.service.ReactiveService
import org.jboss.resteasy.annotations.jaxrs.PathParam
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/reactive-students")
class ReactiveStudentResource(val service: ReactiveService) {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get(): Set<Student> {
        return service.getAllStudents()
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getById(@PathParam id: Long): Student? {
        return service.findStudentById(id)
    }
}