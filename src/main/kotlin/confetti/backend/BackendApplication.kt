package confetti.backend

import com.expediagroup.graphql.server.operations.Query
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component


@SpringBootApplication
class BackendApplication


fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)
}

@Component
class ConfettiQuery : Query {
    fun helloWorld(): String = "Hello KotlinConf!"
}

