package confetti.backend

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import com.expediagroup.graphql.server.ktor.GraphQL
import com.expediagroup.graphql.server.ktor.graphQLPostRoute
import com.expediagroup.graphql.server.ktor.graphiQLRoute
import com.expediagroup.graphql.server.operations.Query
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLType
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import kotlinx.datetime.LocalDateTime
import kotlin.reflect.KClass
import kotlin.reflect.KType

// The GraphQL entry point
class RootQuery : Query {
    fun hello() = "Hello World!!"

    @GraphQLDescription("All the Android Makers sessions!")
    fun sessions(): List<Session> = jsonData.sessions
}

fun Application.graphQLModule() {
    install(GraphQL) {
        schema {
            packages = listOf("confetti.backend")
            queries = listOf(RootQuery())
            hooks = ScalarSchemaGeneratorHooks()
        }
    }
    install(Routing) {
        graphQLPostRoute()
        graphiQLRoute()
    }
}

fun main() {
    embeddedServer(Netty, port = 8080) {
        graphQLModule()
    }.start(wait = true)
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








