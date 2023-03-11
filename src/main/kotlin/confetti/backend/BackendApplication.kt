package confetti.backend

import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import com.expediagroup.graphql.server.operations.Query
import confetti.backend.config.ConfettiSchemaGeneratorHooks
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


@SpringBootApplication
class BackendApplication {
    @Bean
    fun hooks(): SchemaGeneratorHooks {
        return ConfettiSchemaGeneratorHooks()
    }
}


fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)
}

@Component
class GraphQLQuery : Query {
    fun sessions(): List<Session> {
        return jsonData.sessions.map { it.toGraphQL() }
    }
}




