import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.lang.reflect.Type

object SerializationUtil {
    private val JSON = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }
    private val JSON_PRETTY = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        prettyPrint = true
    }

    fun <T> Any.encodeToString(type: Type) = JSON.run {
        encodeToString(serializersModule.serializer(type), this@encodeToString)
    }

    inline fun <reified T> Any.encodeToString() = encodeToString<T>(T::class.java)

    fun <T> String.decodeFromString(type: Type) = JSON.run {
        decodeFromString(serializersModule.serializer(type), this@decodeFromString)
    }

    inline fun <reified T> String.decodeFromString() = decodeFromString<T>(T::class.java) as T
}

