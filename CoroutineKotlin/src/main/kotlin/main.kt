import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.util.*


fun uploadFiles(sizes: List<Long>, force: Boolean = false): Completable = Completable.fromAction {
    println("Uploading...")

    if (!force && sizes.any { it > 100L }) {
        throw IllegalStateException("Too large")
    }

    Thread.sleep(1000)

    println("Uploaded ${sizes.size} file(s)")
}

private fun actualUploadFiles(sizes: List<Long>): Completable = Completable.fromAction {
    println("Uploaded ${sizes.size} file(s)")
}

fun uploadFiles(sizes: List<Long>, onExceeded: Single<Boolean>): Completable = Completable.fromAction {
    println("Uploading...")
    if (sizes.any { it > 100L }) {
        throw IllegalStateException("Too large")
    }
}.onErrorResumeNext { e ->
    println(e)
    if (e is IllegalStateException) {
        onExceeded
            .flatMapCompletable { result ->
                if (result) {
                    Completable.complete()
                } else {
                    Completable.error(e)
                }
            }
    } else Completable.error(e)
}.andThen(actualUploadFiles(sizes))

val scanner = Scanner(System.`in`)

fun userInput(msg: String): Single<Boolean> = Single.create {
    print("[$msg] (yes/no)")
    while (scanner.hasNextLine()) {
        when (scanner.nextLine().trim()) {
            "yes" -> it.onSuccess(true)
            "no" -> it.onSuccess(false)
        }
    }
}

fun main() {
    val files = listOf<Long>(1, 12, 3)
    val disposable = uploadFiles(files)
        .onErrorResumeNext { e ->
            println(e)
            if (e is IllegalStateException) {
                userInput("Do you want to upload large files?")
                    .flatMapCompletable { result ->
                        if (result) {
                            uploadFiles(files, true)
                        } else {
                            Completable.error(e)
                        }
                    }
            } else Completable.error(e)
        }
        .subscribe {
            println("HERE")
        }

//    val disposable = uploadFiles(files, userInput("Size over"))
//        .subscribe {
//            println("HERE")
//        }

    while (!disposable.isDisposed) {
        Thread.sleep(100)
    }
}