package com.example.annotation

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class ExampleAnnotation(val data: String)
