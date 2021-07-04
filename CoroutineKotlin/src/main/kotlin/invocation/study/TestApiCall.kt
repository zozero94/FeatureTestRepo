package invocation.study

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Test(val data: String)

interface TestApiCall {
    @Test("www.kakao.com")
    fun callKakao()

    @Test("www.google.com")
    fun callGoogle()
}