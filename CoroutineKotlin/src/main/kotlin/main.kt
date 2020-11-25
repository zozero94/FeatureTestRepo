import java.util.*

fun main() {


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
