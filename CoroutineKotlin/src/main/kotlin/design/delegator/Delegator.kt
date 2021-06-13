package design.delegator

interface NetworkService {
    fun fetchItems(): String
}

class HttpsConverter(private val delegator: NetworkService) : NetworkService by delegator {
    override fun fetchItems(): String {
        val github = delegator.fetchItems()
        return github.replace("http", "https")
    }
}

class GitHubRequest : NetworkService {
    override fun fetchItems(): String {
        return "http://www.github.com"
    }
}