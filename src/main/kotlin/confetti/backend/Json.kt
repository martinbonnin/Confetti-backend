package confetti.backend

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

private val json = Json {
    ignoreUnknownKeys = true
}

@OptIn(ExperimentalSerializationApi::class)
val jsonData: JsonData by lazy {
    JsonData::class.java.classLoader.getResourceAsStream("all-data.json")!!.use {
        json.decodeFromStream(it)
    }
}

@Serializable
class JsonData(
    val sessions: List<JsonSession>
)

@Serializable
class JsonSession(
    val id: String,
    val title: String,
    val description: String?,
    val startsAt: String,
    val endsAt: String,
)
