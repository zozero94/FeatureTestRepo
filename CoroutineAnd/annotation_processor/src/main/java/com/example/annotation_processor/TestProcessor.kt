package com.example.annotation_processor

import com.example.annotation.ExampleAnnotation
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import java.io.IOException
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier
import javax.lang.model.element.PackageElement
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic


class TestProcessor : AbstractProcessor() {
    private val contextClass = ClassName.get("android.content", "Context")

    private val spec = mutableListOf<MethodSpec?>()
    private var packageName: String? = null

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        println("init")
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment
    ): Boolean {
        println("asdasdasdasdasdasda")
        val elements = roundEnv.getElementsAnnotatedWith(ExampleAnnotation::class.java)
        elements.forEach { element ->
            if (packageName == null) {
                var e = element
                while (e !is PackageElement) {
                    e = e.enclosingElement
                }
                packageName = e.qualifiedName.toString()
            }
            if (element.kind != ElementKind.CLASS) {
                processingEnv.messager
                    .printMessage(Diagnostic.Kind.ERROR, "에러에러에러 존나 에러!!!!")
                return false
            }
            spec.add(generateMethod(element as TypeElement))
        }
        if (roundEnv.processingOver()) {
            try {
                generateJavaFile(spec)
                return true
            } catch (ex: IOException) {
                processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, ex.toString())
            }
        }
        return true
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> =
        mutableSetOf(ExampleAnnotation::class.java.canonicalName)

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()


    private fun generateMethod(element: TypeElement): MethodSpec? {
        return MethodSpec
            .methodBuilder("Zero" + element.simpleName)
            .addModifiers(Modifier.PUBLIC)
            .addParameter(contextClass, "context")
            .addStatement("//으에에엥??")
            .build()
    }

    @Throws(IOException::class)
    private fun generateJavaFile(methodSpecList: List<MethodSpec?>) {
        println("methodSpecList Count = " + methodSpecList.size)
        val builder = TypeSpec.classBuilder("Zero")
        builder.addModifiers(
            Modifier.PUBLIC, Modifier.FINAL
        )
        for (methodSpec in methodSpecList) {
            builder.addMethod(methodSpec)
        }
        val typeSpec = builder.build()
        JavaFile.builder(packageName, typeSpec)
            .build()
            .writeTo(processingEnv.filer)
    }
}