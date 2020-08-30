package com.mostafa.quarkus

import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
abstract class TestDbSetup {
    companion object {
        @Container
        val db = MariaDBContainer<Nothing>("mariadb:latest").apply {
            withDatabaseName("school_schema")
            withUsername("test")
            withPassword("test")
            withExposedPorts(3306)
            withCreateContainerCmdModifier { cmd ->
                cmd
                        .withHostName("localhost")
                        .withPortBindings(PortBinding(Ports.Binding.bindPort(3307), ExposedPort(3306)));
            }
        }
    }
}
