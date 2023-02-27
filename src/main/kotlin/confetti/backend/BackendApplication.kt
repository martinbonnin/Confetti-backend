package confetti.backend

import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import com.expediagroup.graphql.server.operations.Query
import confetti.backend.config.ConfettiSchemaGeneratorHooks
import graphql.language.StringValue
import graphql.schema.*
import kotlinx.datetime.LocalDateTime
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.KType


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
    fun sessions(): List<GraphQLSession> {
        return jsonData.sessions.map { it.toGraphQL() }
    }
}




