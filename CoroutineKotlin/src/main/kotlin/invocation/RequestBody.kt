package invocation

data class RequestBody(val type: String, val body: MutableMap<String, Any>)