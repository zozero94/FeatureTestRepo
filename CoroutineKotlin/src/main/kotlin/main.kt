import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.rx3.asFlow
import kotlinx.coroutines.rx3.asObservable
import kotlinx.coroutines.withContext
import org.reactivestreams.Publisher

fun main() {

    Observable.create<String> {
        it.onNext("코루틴")
        println("craete : ${Thread.currentThread()}")
    }
        .observeOn(Schedulers.io())
        .map {
            println("map1 : ${Thread.currentThread()}")
            "$it 테스트"
        }
        .observeOn(Schedulers.io())
        .asFlow()
        .map {
            println("map2 : ${Thread.currentThread()}")
            withContext(Dispatchers.IO){
                println("map2-1 : ${Thread.currentThread()}")
            }
            it
        }
        .asObservable()
        .map {
            println("map3 : ${Thread.currentThread()}")
            it
        }
        .observeOn(Schedulers.computation())
        .map {
            println("map4 : ${Thread.currentThread()}")
            it
        }
        .subscribeOn(Schedulers.newThread())
        .subscribe {
            println("sub : ${Thread.currentThread()}")
        }

    Thread.sleep(1000)




}



