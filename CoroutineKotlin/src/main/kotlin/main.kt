fun main() {
    val example = Example("", "")
    println(example)
}

data class Example(val data1: String, val data2: String)
data class Example2(val data1: String = "", val data2: String = "")