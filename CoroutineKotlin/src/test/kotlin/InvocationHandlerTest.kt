import invocation.Body
import invocation.Event
import invocation.RequestBody
import invocation.TestEvent
import org.junit.Before
import org.junit.Test

internal class InvocationHandlerTest {

    private lateinit var api: SimpleApi
    interface SimpleApi {
        @TestEvent(FIRST_NAMESPACE)
        fun callFirstApi(@Body("FirstSession") session: String): RequestBody

        @TestEvent(SECOND_NAMESPACE)
        fun callSecondApi(@Body("SecondSession") session: String): RequestBody
    }

    @Before
    fun initApi() {
        api = Event.newEventFactory(SimpleApi::class.java)
    }

    @Test
    fun callApi() {
        val body1 = api.callFirstApi("First")
        val body2 = api.callSecondApi("Second")
        assert(body1.type == FIRST_NAMESPACE)
        assert(body2.type == SECOND_NAMESPACE)
    }

    companion object{
        const val FIRST_NAMESPACE = "firstTest"
        const val SECOND_NAMESPACE = "secondTest"
    }
}