package gson

import com.google.gson.annotations.SerializedName


interface InstructionData
data class ResponseBody<T : Any>(
    @SerializedName("instruction") val instruction: Instruction<T>
)

data class Instruction<DATA : Any>(
    @SerializedName("header") override val header: Header,
    @SerializedName("body") override val body: Body<DATA>,
) : Message<Body<DATA>>()

data class Body<DATA>(
    @SerializedName("token") val token: String,
    @SerializedName("data") val data: DATA
)

sealed class Message<BODY : Any> {
    abstract val header: Header
    abstract val body: BODY
}

data class Header(
    @SerializedName("type") val type: String,
    @SerializedName("messageId") val messageId: String
)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class InstructionType(
    val value: String,
)

@InstructionType("UpdateData")
data class UpdateData(
    @SerializedName("sid") val sid: String,
    @SerializedName("path") val path: String,
    @SerializedName("event") val event: String,
    @SerializedName("code") val code: Int,
    @SerializedName("description") val description: String,
    @SerializedName("object") val changes: Changes,
    @SerializedName("object2") val object2: Count,
) : InstructionData

@InstructionType("ListenTopic")
data class ListenTopic(
    @SerializedName("sid") val sid: String,
    @SerializedName("path") val path: String,
    @SerializedName("from") val from: String,
    @SerializedName("object") val value: Map<String, Any?>
) : InstructionData

data class Changes(
    @SerializedName("on") val on: String,
    @SerializedName("by") val by: String,
    @SerializedName("at") val at: String,
) {
    companion object {
        const val CHANGED = "changed"
    }
}

data class Count(
    @SerializedName("count") val count: Int,
) {
    companion object {
        const val COUNT = "count"
    }
}