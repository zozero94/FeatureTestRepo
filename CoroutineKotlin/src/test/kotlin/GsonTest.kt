import com.google.gson.*
import gson.InstructionData
import gson.ListenTopic
import gson.UpdateData
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Type

class GsonTest {

    val updateData = """
       {"instruction":{"header":{"type":"Vendor.Kakaoi.FlashSync.UpdateData","messageId":"096e0d8114c80717-8310e89da80c29f7"},"body":{"token":"kep/_/193be22eda63cbda","data":{"sid":"exampleSid",path":"/fql-jpt-rum","object": {"on": "changed","by": "yourUID","at": "/users/alice/favorite"}}}}}
    """

    val listenTopic = """
        {"instruction":{"header":{"type":"Vendor.Kakaoi.FlashSync.ListenTopic","messageId":"096e0d8114c80717-8310e89da80c29f7"},"body":{"token":"kep/_/193be22eda63cbda","data":{"path":"/fql-jpt-rum","from":"e1ecbf5e-5db2-4d8c-97ed-aea06e21efe8","object":{"custom":"o"}}}}}
    """

    private lateinit var gson: Gson

    @Before
    fun setUp() {
        val builder = GsonBuilder().registerTypeAdapter(InstructionData::class.java, InstructionDeserializer())
        gson = builder.create()
    }

    class InstructionDeserializer : JsonDeserializer<InstructionData> {
        private val gson = Gson()
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): InstructionData {
            val jsonObject = json.asJsonObject
            val instruction = jsonObject.get("instruction").asJsonObject
            val header = instruction.get("header").asJsonObject
            val body = instruction.get("body").asJsonObject
            val data = body.get("data")
            val type = header.get("type").asString

            return if (type == "Vendor.Kakaoi.FlashSync.ListenTopic") {
                gson.fromJson(data, ListenTopic::class.java)
            } else if (type == "Vendor.Kakaoi.FlashSync.UpdateData") {
                gson.fromJson(data, UpdateData::class.java)
            } else {
                throw ClassNotFoundException("없듬ㅋ")
            }
        }
    }


    @Test
    fun ListenTopicTest() {
        val instructionData = gson.fromJson(listenTopic, InstructionData::class.java)
        assert(instructionData is ListenTopic)
    }

    @Test
    fun UpdateDataTest() {
        val instructionData = gson.fromJson(updateData, InstructionData::class.java)
        assert(instructionData is UpdateData)

    }
}