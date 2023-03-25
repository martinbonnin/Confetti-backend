package confetti.backend

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

@Serializable
class JsonSpeaker(
    val id: String,
    val firstName: String,
    val lastName: String,
)

@Serializable
class JsonRoom(
    val id: Int,
    val name: String,
)

@Serializable
class JsonSession(
    val id: String,
    val title: String,
    val description: String?,
    val startsAt: String,
    val endsAt: String,
    val speakers: List<String>,
    val roomId: Int
)

@Serializable
class JsonData(
    val sessions: List<JsonSession>,
    val speakers: List<JsonSpeaker>,
    val rooms: List<JsonRoom>,
)

@OptIn(ExperimentalSerializationApi::class)
val jsonData: JsonData by lazy {
    JsonData::class.java.classLoader.getResourceAsStream("all-data.json")!!.use {
        Json.decodeFromStream(it)
    }
}

