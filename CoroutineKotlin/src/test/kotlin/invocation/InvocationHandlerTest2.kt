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
        api.callKakao()
    }

    @Test
    fun phaseCallGoogle() {
        api.callGoogle()
    }
}