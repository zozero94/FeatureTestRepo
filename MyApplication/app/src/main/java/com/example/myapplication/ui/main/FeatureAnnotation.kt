package com.example.myapplication.ui.main

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class FeatureAnnotation(val namespace: String)