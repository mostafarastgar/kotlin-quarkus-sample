package com.mostafa.quarkus.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tb_courses")
class Course() {
    constructor(id: Long, name: String, unit: Int) : this(name, unit) {
        this.id = id
    }

    constructor(name: String, unit: Int) : this() {
        this.name = name
        this.unit = unit
    }

    @Id
    @GeneratedValue
    @Column(name = "course_id", nullable = false)
    var id: Long? = null

    @Column(name = "name", nullable = false)
    var name: String? = null

    @Column(name = "unit", nullable = false)
    var unit: Int? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Course

        if (id != other.id) return false
        if (name != other.name) return false
        if (unit != other.unit) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (unit ?: 0)
        return result
    }
}