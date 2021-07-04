package invocation

import invocation.study.TestApiCall
import invocation.study.createApi
import org.junit.Before
import org.junit.Test

class InvocationHandlerTest2 {
    private lateinit var api: TestApiCall

    @Before
    fun setUp() {
        api = createApi()
    }

    @Test
    fun phaseCallKakao() {
        val res = api.callKakao()
        println(res)
        assert(res.author=="zero.dev")
    }

    @Test
    fun phaseCallGoogle() {
        val res = api.callGoogle()
        println(res)
        assert(res.author=="로버트 마틴")
    }
}