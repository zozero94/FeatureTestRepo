import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ListScanTest {

    @Test
    fun inSideListTest() {
        runBlocking {
            merge(dataEmitter(),randomDataEmitter())
                .scan(mutableListOf<String>()) { list, data ->
                    list.add(data)
                    list
                }
                .onEach { println(it) }
                .launchIn(this)
        }
    }

    @Test
    fun outSideListTest() {
        runBlocking {
            val list = mutableListOf<String>()
            merge(dataEmitter(),randomDataEmitter())
                .map {
                    list.add(it)
                    list
                }
                .onEach {
                    println(it)
                }
                .launchIn(this)
        }
    }


    private fun randomDataEmitter() = flow {
        var data = 0
        while (data != 5) {
            delay((1000..5000).random().toLong())
            emit("random : ${data++}")
        }
    }

    private fun dataEmitter() = flow {
        var data = 0
        while (data != 5) {
            delay(1000)
            emit("${data++}")
        }
    }
}


