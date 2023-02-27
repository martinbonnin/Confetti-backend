package confetti.backend.config

import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import graphql.schema.GraphQLType
import kotlinx.datetime.LocalDateTime
import kotlin.reflect.KClass
import kotlin.reflect.KType

class ConfettiSchemaGeneratorHooks : SchemaGeneratorHooks {
    override fun willGenerateGraphQLType(type: KType): GraphQLType? =
        when (type.classifier as? KClass<*>) {
            LocalDateTime::class -> graphqlLocalDateTimeScalar
            else -> null
        }
}