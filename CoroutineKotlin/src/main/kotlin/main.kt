fun main() {
    val api = Event.newEventFactory(MySimpleApi::class.java)
    val body1 = api.callFirstApi("첫번 째")
    val body2 = api.callSecondApi("두번 째")

    println(body1)
    println(body2)
}


interface MySimpleApi {
    @TestEvent("firstTest")
    fun callFirstApi(@Body("FirstSession") session: String): RequestBody

    @TestEvent("secondTest")
    fun callSecondApi(@Body("SecondSession") session: String): RequestBody
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class TestEvent(val namespace: String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Body(val value: String)
