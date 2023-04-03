package confetti.backend

import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import com.expediagroup.graphql.server.operations.Query
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLType
import kotlinx.datetime.LocalDateTime
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import kotlin.reflect.KClass
import kotlin.reflect.KType


// The GraphQL entry point
class RootQuery : Query {
    fun sessions(): List<Session> {
        return jsonData.sessions
    }
}

// The Spring Boot entry point
@EnableAutoConfiguration
class ConfettiApplication

// The Jar entry point
fun main() {
    runApplication<ConfettiApplication> {
        addInitializers(beans {
            bean<ScalarSchemaGeneratorHooks>()
            bean<RootQuery>()
        })
    }
}


class ScalarSchemaGeneratorHooks: SchemaGeneratorHooks {
    override fun willGenerateGraphQLType(type: KType): GraphQLType? =
        when (type.classifier as? KClass<*>) {
            LocalDateTime::class -> LocalDateTimeScalar
            else -> null
        }
}

object LocalDateTimeCoercing : Coercing<LocalDateTime, String> {
    override fun parseValue(input: Any) = LocalDateTime.parse(input.toString())

    override fun parseLiteral(input: Any) = LocalDateTime.parse((input as StringValue).value)

    override fun serialize(dataFetcherResult: Any) = dataFetcherResult.toString()
}

// Add "LocalDateTime" as a new type in the GraphQL schema
val LocalDateTimeScalar = GraphQLScalarType.newScalar()
    .name("LocalDateTime")
    .description("A type representing a formatted kotlinx.datetime.LocalDateTime")
    .coercing(LocalDateTimeCoercing)
    .build()








