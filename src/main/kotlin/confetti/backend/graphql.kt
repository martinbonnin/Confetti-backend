package confetti.backend

import com.expediagroup.graphql.generator.scalars.ID
import kotlinx.datetime.LocalDateTime

class Session(
    val id: ID,
    val title: String,
    val description: String?,
    val start: LocalDateTime,
    val end: LocalDateTime,
    private val speakerIds: List<ID>,
    private val roomId: ID,
) {
    fun speakers(): List<Speaker> {
        return speakerIds.map { speakerId ->
            jsonData.speakers.find { it.id == speakerId.value }?.toGraphQL() ?: error("Cannot find speaker: $speakerId")
        }
    }

    fun room(): Room {
        return jsonData.rooms.find { it.id.toString() == roomId.value }?.toGraphQL() ?: error("Cannot find room: $roomId")
    }

    val type = "talk"
}

class Speaker(
    val id: ID,
    val name: String,
)

class Room(
    val id: ID,
    val name: String,
)