package confetti.backend

import com.expediagroup.graphql.generator.scalars.ID
import kotlinx.datetime.LocalDateTime

fun JsonSession.toSession(): Session {
    return Session(
        id = ID(id),
        title = title,
        description = description,
        start = LocalDateTime.parse(startsAt),
        end = LocalDateTime.parse(endsAt),
        speakerIds = speakers.map { ID(it) },
        roomId = ID(roomId.toString()),
    )
}

fun JsonSpeaker.toSpeaker(): Speaker {
    return Speaker(
        id = ID(id),
        name = "$firstName $lastName",
    )
}

fun JsonRoom.toRoom(): Room {
    return Room(
        id = ID(id.toString()),
        name = name,
    )
}