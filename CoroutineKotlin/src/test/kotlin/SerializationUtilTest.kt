import SerializationUtil.decodeFromString
import SerializationUtil.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test

internal class SerializationUtilTest {
    private lateinit var json: String

    @Before
    fun initJsonString() {
        json = """
    {
        "name": "hello world",
        "language": "korea"
    }
        """
    }

    @Test
    fun decodeFromStringTest() {
        val projectByUtil = json.decodeFromString<Project>()
        val projectBySerialization = Json.decodeFromString<Project>(json)

        assert(projectByUtil == projectBySerialization)
    }

    @Test
    fun encodeToStringTest() {
        val project = Project("test1", "korea")

        val projectByUtil = project.encodeToString<Project>()
        val projectSerialization = Json.encodeToString(project)

        assert(projectByUtil == projectSerialization)
    }

    @Test
    fun encodeDefaultsTest() {
        val project = Project()

        val emptyResult = Json.encodeToString(project)
        assert(emptyResult == "{}")
        val defaultResult = Json { encodeDefaults = true }.encodeToString(project)

        assert(
            defaultResult == """
            {"name":"test","language":"english"}
        """.trimIndent()
        )
    }
}