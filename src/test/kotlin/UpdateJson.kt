@file:OptIn(ExperimentalSerializationApi::class)

import confetti.backend.Data
import confetti.backend.Room
import confetti.backend.Session
import confetti.backend.Speaker
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.jupiter.api.Test
import java.io.File

@Suppress("JSON_FORMAT_REDUNDANT")
class UpdateJson {
    @Test
    fun test() {

        val jsonData = OkHttpClient()
            .newCall(
                Request.Builder()
                    .url("https://sessionize.com/api/v2/4fyvz46j/view/All")
                    .get()
                    .build()
            ).execute()
            .body!!
            .byteStream()
            .use {
                Json {
                    ignoreUnknownKeys = true
                }.decodeFromStream<JsonData>(it)
            }

        File("src/main/resources/all-data.json").outputStream().use {
            Json.encodeToStream(jsonData.toData(), it)
        }
    }
}

private fun JsonData.toData() = Data(
    sessions = sessions.map { it.toSession() }.filter {
        LocalDateTime.parse(it.start).date.dayOfMonth != 12
    },
    speakers = speakers.map { it.toSpeaker() },
    rooms = rooms.map { it.toRoom() }
)

private fun JsonSession.toSession() = Session(
    id = id,
    title = title,
    description = description,
    start = startsAt,
    end = endsAt,
    speakerIds = speakers,
    roomId = roomId.toString()
)

private fun JsonSpeaker.toSpeaker() = Speaker(
    id = id,
    name = "$firstName $lastName",
    tagline = tagLine,
)

private fun JsonRoom.toRoom() = Room(
    id = id.toString(),
    name = name,
)

@Serializable
class JsonSpeaker(
    val id: String,
    val firstName: String,
    val lastName: String,
    val tagLine: String?
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