load("@rules_jvm_external//:defs.bzl", "artifact")
load("@rules_android//android:rules.bzl", "android_library")

package(default_visibility = ["//visibility:private"])
load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_android_library")

MANIFEST = "AndroidManifest.xml"
PACKAGE = "com.example.myapplication"
android_library(
    name = "basic_lib",
    custom_package = PACKAGE,
    manifest = MANIFEST,
    enable_data_binding = True,
    resource_files = glob(["res/**"]),
     deps = [
            artifact("androidx.constraintlayout:constraintlayout"),
            artifact("com.google.android.material:material"),
        ],
)

kt_android_library(
    name = "bazel_kt",
    srcs = glob(["java/**/*.kt"]),
    deps = [
        ":basic_lib",
        artifact("androidx.appcompat:appcompat"),
        artifact("androidx.fragment:fragment"),
        artifact("androidx.core:core"),
        artifact("androidx.lifecycle:lifecycle-runtime"),
        artifact("androidx.lifecycle:lifecycle-viewmodel"),
        artifact("androidx.lifecycle:lifecycle-common"),
        artifact("androidx.drawerlayout:drawerlayout"),
        artifact("org.jetbrains.kotlinx:kotlinx-coroutines-core"),
        artifact("org.jetbrains.kotlinx:kotlinx-coroutines-android"),
    ]
)

android_binary(
    name = "MyApllication",
    manifest = MANIFEST,
    custom_package = PACKAGE,
     manifest_values = {
            "minSdkVersion": "21",
            "versionCode" : "2",
            "versionName" : "0.2",
            "targetSdkVersion": "29",
        },
    multidex = "native",
    deps = [
        ":basic_lib",
        ":bazel_kt",
        artifact("androidx.appcompat:appcompat"),
    ],
)
