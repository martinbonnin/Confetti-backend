package confetti.backend

import com.expediagroup.graphql.generator.scalars.ID
import kotlinx.datetime.LocalDateTime

class GraphQLSession(
    val id: ID,
    val title: String,
    val description: String?,
    val start: LocalDateTime,
    val end: LocalDateTime,
    private val speakerIds: List<ID>,
    private val roomId: ID,
) {
    fun speakers(): List<GraphQLSpeaker> {
        return speakerIds.map { speakerId ->
            jsonData.speakers.find { it.id == speakerId.value }?.toGraphQL() ?: error("Cannot find speaker: $speakerId")
        }
    }

    fun room(): GraphQLRoom {
        return jsonData.rooms.find { it.id.toString() == roomId.value }?.toGraphQL() ?: error("Cannot find room: $roomId")
    }
}

class GraphQLSpeaker(
    val id: ID,
    val firstName: String,
    val lastName: String,
)

class GraphQLRoom(
    val id: ID,
    val name: String,
)