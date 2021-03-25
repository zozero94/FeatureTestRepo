import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

private val disposableMap = mutableMapOf<String, Disposable>()
private val cancelMap = mutableSetOf<String>()

private val newThread = Schedulers.from(Executors.newCachedThreadPool())

//todo 1 : 여러 테스크 중간에 하나의 스트림이 dispose 가 되었을 때, 나머지 테스크가 진행이 되는가?
//todo 2 : 모든 subject emit 이후 disposable 이 dispose 가 되는가?? -> 당연히 안됨.
fun main() {

    val subject = BehaviorSubject.create<String>()
    val disposable = subject
        .observeOn(newThread)
        .concatMapEager {
            prepare(UploadFile(it)).toObservable()
        }
        .concatMapSingle {
            startUpload(it)
        }
        .subscribe {
            println("subscribe : $it")
        }


    //data 발행한다.
    Thread {
        (0..3).forEach {
            subject.onNext("task$it")
        }
    }.start()


    //발행 도중에 특정 테스크를 취소한다.
    Thread {
        Thread.sleep(6000)
        println()
        println("/////////")
        println("task2 dispose ${disposableMap["task2"]}")
        disposableMap["task2"]?.dispose()
        disposableMap.forEach(::println)
        println("/////////")
        println()

    }.start()

}

data class UploadFile(val name: String)

val list = listOf(100, 500, 300, 400, 100)
var index = 0
fun prepare(file: UploadFile): Single<UploadFile> {
    return Single.create {
        println("prepare Start $file")
        Thread.sleep(list[index++].toLong())
        println("prepare End $file")
        println()
        it.onSuccess(file)
    }
}

fun startUpload(file: UploadFile): Single<Boolean> {
    return Observable.timer(2, TimeUnit.SECONDS)
        .flatMapSingle {
            Single.create<Boolean> {
                println("startUpload $file")
                if (!it.isDisposed) {
                    it.onSuccess(true)
                }
            }
        }.single(false)
        .doOnDispose {
            println("dispose ${file.name}")
        }
        .doOnSubscribe {
            disposableMap[file.name] = it
        }
}