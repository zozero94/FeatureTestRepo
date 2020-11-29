package invocation

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.*


object Event {
    fun <T> newEventFactory(clazz: Class<T>): T =
        Proxy.newProxyInstance(clazz.classLoader, arrayOf<Class<*>>(clazz), EventInvocationHandler()) as T


    private class EventInvocationHandler : InvocationHandler {
        @Throws(Throwable::class)
        override fun invoke(proxy: Any, method: Method, args: Array<Any>): Any? {
            if (method.declaringClass === Any::class.java) {
                return invokeObjectMethod(proxy, method, args)
            }
            val eventRequest: TestEvent = method.getAnnotation(TestEvent::class.java)

            val type: String = eventRequest.namespace
            val body: MutableMap<String, Any> = HashMap()
            val paramAnnotations: Array<Array<Annotation>> = method.parameterAnnotations
            for (i in paramAnnotations.indices) {
                val arg = args[i]
                val bodyParam: Body? = getAnnotation(paramAnnotations[i], Body::class.java)
                if (bodyParam != null) {
                    val key: String = bodyParam.value
                    body[key] = arg
                } else {
                    throw IllegalArgumentException("BodyParam annotation is missing: $method")
                }
            }
            return RequestBody(type, body)
        }

        private fun <A : Annotation?> getAnnotation(annotations: Array<Annotation>, type: Class<A>): A? {
            for (annotation in annotations) {
                if (annotation.annotationClass.java == type) {
                    return annotation as A
                }
            }
            return null
        }

        private fun invokeObjectMethod(o: Any, method: Method, args: Array<Any>): Any? {
            when (method.name) {
                "toString" -> return o.toString()
                "hashCode" -> return System.identityHashCode(o)
                "equals" -> return o === args[0]
            }
            return null
        }

    }
}
