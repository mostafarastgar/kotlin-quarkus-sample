package com.mostafa.quarkus.service

import com.mostafa.quarkus.entity.Course
import com.mostafa.quarkus.entity.Student
import io.smallrye.mutiny.Multi
import io.vertx.mutiny.mysqlclient.MySQLPool
import io.vertx.mutiny.sqlclient.Row
import io.vertx.mutiny.sqlclient.Tuple
import org.eclipse.microprofile.metrics.MetricUnits
import org.eclipse.microprofile.metrics.annotation.Counted
import org.eclipse.microprofile.metrics.annotation.Timed
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ReactiveService(private val client: MySQLPool) {

    @Counted(name = "getAllStudentsReactiveChecks",
            description = "How many primality checks have been performed.")
    @Timed(name = "getAllStudentsReactiveTimer",
            description = "A measure of how long it takes to perform the primality test.",
            unit = MetricUnits.MILLISECONDS)
    fun getAllStudents(): Set<Student> {
        val students = mutableMapOf<Long, Student>()
        fetchStudent(null).collectItems().asList().await().indefinitely().forEach {
            if (students[it.id] == null) {
                students[it.id!!] = it
            }
            students[it.id]!!.courses.add(it.courses.first())
        }
        return students.values.toSet()
    }

    fun findStudentById(id: Long): Student? {
        val items = fetchStudent(id).collectItems().asList().await().indefinitely()
        if (items.size > 0) {
            items.first().courses.addAll(items.map { it.courses.first() })
            return items.first()
        }
        return null;
    }

    private fun fetchStudent(id: Long?): Multi<Student> {

        val result = if (id == null) client.query("SELECT s.student_id, s.name as student_name" +
                ", s.student_number, c.course_id, c.name as course_name, c.unit " +
                "FROM tb_student_courses sc " +
                "INNER JOIN tb_courses c ON c.course_id = sc.course_id " +
                "INNER JOIN tb_students s ON s.student_id = sc.student_id").execute()
        else client.preparedQuery("SELECT s.student_id, s.name as student_name, s.student_number, " +
                "c.course_id, c.name as course_name, c.unit FROM tb_student_courses sc " +
                "INNER JOIN tb_courses c ON c.course_id = sc.course_id " +
                "INNER JOIN tb_students s ON s.student_id = sc.student_id " +
                "WHERE s.student_id = ?").execute(Tuple.of(id))
        return result.onItem().transformToMulti { set -> Multi.createFrom().iterable(set) }
                .onItem().transform { row ->
                    val student = fromRowToStudent(row)
                    student.courses.add(fromRowToCourse(row))
                    student
                }
    }

    fun getAllCourses(): Multi<Course> {
        return client.query("SELECT c.course_id, c.name as course_name, c.unit FROM tb_courses c")
                .execute().onItem().transformToMulti { set -> Multi.createFrom().iterable(set) }
                .onItem().transform { fromRowToCourse(it) }
    }

    companion object {
        private fun fromRowToStudent(row: Row): Student {
            return Student(row.getLong("student_id"), row.getString("student_name"),
                    row.getInteger("student_number"))
        }

        private fun fromRowToCourse(row: Row): Course {
            return Course(row.getLong("course_id"), row.getString("course_name"),
                    row.getInteger("unit"))
        }
    }

}