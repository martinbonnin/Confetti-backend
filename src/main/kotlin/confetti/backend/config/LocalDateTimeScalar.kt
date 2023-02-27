package confetti.backend.config

import graphql.language.StringValue
import graphql.schema.*
import kotlinx.datetime.LocalDateTime

val graphqlLocalDateTimeScalar = GraphQLScalarType.newScalar()
    .name("LocalDate")
    .description("A type representing a formatted kotlinx.datetime.LocalDateTime")
    .coercing(LocalDateCoercing)
    .build()!!

object LocalDateCoercing : Coercing<LocalDateTime, String> {
    override fun parseValue(input: Any): LocalDateTime = runCatching {
        LocalDateTime.parse(serialize(input))
    }.getOrElse {
        throw CoercingParseValueException("Expected valid LocalDateTime but was $input")
    }

    override fun parseLiteral(input: Any): LocalDateTime {
        val str = (input as? StringValue)?.value
        return runCatching {
            LocalDateTime.parse(str!!)
        }.getOrElse {
            throw CoercingParseLiteralException("Expected valid LocalDateTime literal but was $str")
        }
    }

    override fun serialize(dataFetcherResult: Any): String = runCatching {
        dataFetcherResult.toString()
    }.getOrElse {
        throw CoercingSerializeException("Data fetcher result $dataFetcherResult cannot be serialized to a String")
    }
}