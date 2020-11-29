import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Project(
    @SerialName("name") val name: String = "test",
    @SerialName("language") val language: String = "english"
)