package confetti.backend

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

@Serializable
class Speaker(
    val id: String,
    private val firstName: String,
    private val lastName: String,
) {
    fun name() = "$firstName $lastName"
}

@Serializable
class Room(
    val id: String,
    val name: String,
)

@Serializable
class Session(
    val id: String,
    val title: String,
    val description: String?,
    val start: String,
    val end: String,
    val speakerIds: List<String>,
    val roomId: String
) {
    fun speakers() = speakerIds.map { speakerId ->
        jsonData.speakers.find { it.id == speakerId }!!
    }

    fun room() = jsonData.rooms.find { it.id == roomId }!!
}

@Serializable
class Data(
    val sessions: List<Session>,
    val speakers: List<Speaker>,
    val rooms: List<Room>,
)

@OptIn(ExperimentalSerializationApi::class)
val jsonData: Data by lazy {
    Data::class.java.classLoader.getResourceAsStream("all-data.json")!!.use {
        Json.decodeFromStream(it)
    }
}

