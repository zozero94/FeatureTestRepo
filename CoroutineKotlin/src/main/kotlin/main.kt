import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

fun main() {
    Observable.interval(500, TimeUnit.MILLISECONDS)
        .map {
            it+1
        }
        .subscribe {
            println(it)
        }
    Thread.sleep(10000)
}

