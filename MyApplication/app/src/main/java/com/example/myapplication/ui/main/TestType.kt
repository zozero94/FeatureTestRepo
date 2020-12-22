package com.example.myapplication.ui.main

import android.util.Log
import java.lang.IllegalArgumentException
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

object TestType {
    fun <T> newFactory(clazz: Class<T>): T =
        Proxy.newProxyInstance(
            clazz.classLoader,
            arrayOf<Class<*>>(clazz),
            EventInvocationHandler()
        ) as T


    private class EventInvocationHandler : InvocationHandler {
        @Throws(Throwable::class)
        override fun invoke(proxy: Any, method: Method, args: Array<Any>): Any? {
            if (method.declaringClass === Any::class.java) {
                return invokeObjectMethod(proxy, method, args)
            }
            val feature: FeatureAnnotation =
                method.getAnnotation(FeatureAnnotation::class.java)
                    ?: throw ClassNotFoundException("FeatureAnnotation Not Found")

            val featureName: String = feature.namespace

            if(args.isEmpty() || args.size>1) throw IllegalArgumentException("argument is not 1")

            return args.first() to featureName
        }

        private fun <A : Annotation?> getAnnotation(
            annotations: Array<Annotation>,
            type: Class<A>
        ): A? {
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