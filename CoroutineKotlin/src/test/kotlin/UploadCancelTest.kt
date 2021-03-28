import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import java.util.concurrent.ConcurrentHashMap

class UploadCancelTest {
    private lateinit var map: ConcurrentHashMap<String, PublishSubject<Void>>
    private lateinit var urls: List<String>
    private val resultUrls = listOf("[someurlr1]", "[someurl2]", "[someurl3]")

    @Before
    fun setUp() {
        map = ConcurrentHashMap<String, PublishSubject<Void>>()
        urls = listOf("someurlr1", "someurl2", "someurl3")
    }

    @Test
    fun generalUploadCase() {
        uploadTask(urls)
            .observeOn(Schedulers.io())
            .test()
            .assertSubscribed()
            .assertResult(*resultUrls.toTypedArray())

    }

    @Test
    fun cancelUploadCase() {
        uploadTask(urls)
            .observeOn(Schedulers.io())
            .doOnNext {
                val ps = map["someurl2"]
                ps?.onComplete()
            }
            .test()
            .assertSubscribed()
            .assertResult(*resultUrls.toTypedArray())


    }

    private fun uploadTask(urls: List<String>): Observable<String> {
        return Observable.fromIterable(urls)
            .concatMap { url ->
                val subject = PublishSubject.create<Void>()
                if (map.putIfAbsent(url, subject) == null) {
                    return@concatMap downloadFile(url)
                        .takeUntil(subject)
                        .doAfterTerminate { map.remove(url) }
                        .doOnDispose { map.remove(url) }
                }
                Observable.empty()
            }
    }

    private fun downloadFile(url: String): Observable<String> {
        return Observable.just(url)
            .flatMap {
                Observable.just("[$it]")
            }
    }
}
