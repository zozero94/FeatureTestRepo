import design.delegator.GitHubRequest
import design.delegator.HttpsConverter
import design.delegator.NetworkService
import org.junit.Before
import org.junit.Test

class DelegatorTest {
    private lateinit var originRequest: NetworkService
    private lateinit var converter: NetworkService

    @Before
    fun setUp() {
        originRequest = GitHubRequest()
        converter = HttpsConverter(originRequest)
    }

    @Test
    fun originalRequestTest() {
        val originUrl = originRequest.fetchItems()
        assert(originUrl.contains("http"))
    }

    @Test
    fun convertHttpToHttps() {
        val convertRequest = converter.fetchItems()
        assert(convertRequest.contains("https"))
    }
}