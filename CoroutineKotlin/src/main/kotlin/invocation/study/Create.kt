package invocation.study

import java.lang.reflect.Proxy

inline fun <reified T> createApi() = Proxy.newProxyInstance(
    ClassLoader.getSystemClassLoader(),
    arrayOf(TestApiCall::class.java)
) { _, method, _ ->

    val annotation = method.getAnnotation(Test::class.java)

    println("${BuildConfig.currentPhase().prefix}${annotation.data}")

    null

} as T