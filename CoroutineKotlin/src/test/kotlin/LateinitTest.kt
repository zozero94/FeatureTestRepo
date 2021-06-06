import org.junit.Test

class LateinitTest {
    private lateinit var snapshot: String

    @Test
    fun lateiniteTest() {
        val result = if (snapshot is CharSequence) {
            snapshot.isEmpty()
        } else {
            false
        }
        assert(result)
    }
}