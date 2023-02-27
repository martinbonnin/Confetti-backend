package confetti.backend

import com.expediagroup.graphql.generator.scalars.ID
import kotlinx.datetime.LocalDateTime

fun JsonSession.toGraphQL(): GraphQLSession {
    return GraphQLSession(
        id = ID(id),
        title = title,
        description = description,
        start = LocalDateTime.parse(startsAt),
        end = LocalDateTime.parse(endsAt)
    )
}

class GraphQLSession(
    val id: ID,
    val title: String,
    val description: String?,
    val start: LocalDateTime,
    val end: LocalDateTime,
)

