package invocation.study

enum class BuildConfig(val prefix: String) {
    SANDBOX("https://sandbox/"), BETA("https://beta/"), REAL("https://real/");

    companion object {
        fun currentPhase() = values().random()
    }
}