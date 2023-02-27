package confetti.backend

import com.expediagroup.graphql.generator.scalars.ID
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
class GraphQLQuery : Query {
    fun sessions(): List<GraphQLSession> {
        return jsonData.sessions.map { it.toGraphQL() }
    }
}

fun JsonSession.toGraphQL(): GraphQLSession {
    return GraphQLSession(
        id = ID(id),
        title = title,
        description = description
    )
}
class GraphQLSession(
    val id: ID,
    val title: String,
    val description: String?,
)

