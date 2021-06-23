import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CollectAndLaunchTest {
    @Test
    fun onEachTest() {

        runBlocking {
            subscribe().onEach {
                println(it)
            }.launchIn(this@runBlocking)

            println("onEach : 먼저 호출 됨")

//            val flow=subscribe()
//            launch { flow.collect{ println(it)} }
//            println("onEach : 먼저 호출 됨")
        }

    }

    @Test
    fun collectTest() {

        runBlocking {
            subscribe().collect {
                println(it)
            }
            println("collect : 나중에 호출 됨")
        }

    }
}

fun subscribe() = flow {
    var count = 0
    while (true) {
        delay(2000)
        emit("방출띠 : ${count++}")
    }
}