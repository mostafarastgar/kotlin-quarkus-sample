package com.mostafa.quarkus.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "tb_students")
class Student() {
    constructor(id: Long, name: String, stNumber: Int) : this() {
        this.id = id
        this.name = name
        this.stNumber = stNumber
    }

    constructor(name: String, stNumber: Int, courses: MutableSet<Course>) : this() {
        this.name = name
        this.stNumber = stNumber
        this.courses = courses
    }

    @Id
    @GeneratedValue
    @Column(name = "student_id", nullable = false)
    var id: Long? = null

    @Column(name = "name", nullable = false)
    var name: String? = null

    @Column(name = "student_number", nullable = false, unique = true)
    var stNumber: Int? = null

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_student_courses",
            joinColumns = [JoinColumn(name = "student_id")],
            inverseJoinColumns = [JoinColumn(name = "course_id")]
    )
    var courses: MutableSet<Course> = mutableSetOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Student

        if (id != other.id) return false
        if (name != other.name) return false
        if (stNumber != other.stNumber) return false
        if (courses != other.courses) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (stNumber ?: 0)
        result = 31 * result + courses.hashCode()
        return result
    }
}