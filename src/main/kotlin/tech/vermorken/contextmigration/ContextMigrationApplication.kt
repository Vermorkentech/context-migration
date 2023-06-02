package tech.vermorken.contextmigration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ContextMigrationApplication

fun main(args: Array<String>) {
    runApplication<ContextMigrationApplication>(*args)
}
