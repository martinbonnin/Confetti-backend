package confetti.backend

import com.expediagroup.graphql.generator.scalars.ID
import kotlinx.datetime.LocalDateTime

fun JsonSession.toGraphQL(): GraphQLSession {
    return GraphQLSession(
        id = ID(id),
        title = title,
        description = description,
        start = LocalDateTime.parse(startsAt),
        end = LocalDateTime.parse(endsAt),
        speakerIds = speakers.map { ID(it) },
        roomId = ID(roomId.toString()),
    )
}

fun JsonSpeaker.toGraphQL(): GraphQLSpeaker {
    return GraphQLSpeaker(
        id = ID(id),
        name = "$firstName $lastName",
    )
}

fun JsonRoom.toGraphQL(): GraphQLRoom {
    return GraphQLRoom(
        id = ID(id.toString()),
        name = name,
    )
}