import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

private val disposableMap = mutableMapOf<String, Disposable>()

//todo 1 : 테스크 중간에 dispose 가 되었을 때, 나머지 테스크가 진행이 되는가?
//todo 2 : 모든 subject emit 이후 disposable 이 dispose 가 되는가??
fun main() {

    val subject = BehaviorSubject.create<String>()

    val disposable = subject
        .concatMapEager {
            newTask(it).toObservable()
        }
        .subscribeOn(Schedulers.computation())
        .subscribe {
            println("subscribe : $it")
        }

    subject.onNext("task1")
    subject.onNext("task2")
    subject.onNext("task3")
    subject.onNext("task4")
    Thread.sleep(1000)
    disposableMap.forEach {
        println("${it.key} ${it.value}")
    }
    disposableMap["task2"]?.dispose()
    println(disposable.isDisposed)

    Thread.sleep(5000)


}

fun newTask(taskId: String): Single<String> {
    return Single.create<String> {
        Thread.sleep(2000)
        it.onSuccess("emit Task $taskId")
    }
        .doOnSubscribe {
            disposableMap[taskId] = it
        }
}