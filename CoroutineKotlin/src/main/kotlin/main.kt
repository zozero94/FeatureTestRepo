import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main() {
    val example by Example<Int>()
    val b by Delegate()
    println(b)
    println(example)

}
class Example<T : Any> : ReadWriteProperty<Any?, T?> {
    var item: T? = null
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        println(property.name)
        item = value
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        println(property.name)
        return item
    }

}
class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}
