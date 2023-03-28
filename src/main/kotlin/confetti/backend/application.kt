package confetti.backend

import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import com.expediagroup.graphql.server.operations.Query
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLType
import kotlinx.datetime.LocalDateTime
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.KType


@Component
class GraphQLQuery : Query {
    fun sessions(): List<Session> {
        return jsonData.sessions.map { it.toSession() }
    }
}

@SpringBootApplication
class BackendApplication {
    @Bean
    fun hooks(): SchemaGeneratorHooks = object : SchemaGeneratorHooks {
        override fun willGenerateGraphQLType(type: KType): GraphQLType? =
            when (type.classifier as? KClass<*>) {
                LocalDateTime::class -> LocalDateTimeScalar
                else -> null
            }
    }
}

fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)
}

object LocalDateTimeCoercing : Coercing<LocalDateTime, String> {
    override fun parseValue(input: Any) = LocalDateTime.parse(input.toString())

    override fun parseLiteral(input: Any) = LocalDateTime.parse((input as StringValue).value)

    override fun serialize(dataFetcherResult: Any) = dataFetcherResult.toString()
}

val LocalDateTimeScalar = GraphQLScalarType.newScalar()
    .name("LocalDateTime")
    .description("A type representing a formatted kotlinx.datetime.LocalDateTime")
    .coercing(LocalDateTimeCoercing)
    .build()







