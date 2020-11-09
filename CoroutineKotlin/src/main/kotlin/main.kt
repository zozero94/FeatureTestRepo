import java.util.*

fun main() {
    Solution().run {
        solution(
            arrayOf(
                "0001 3 95",
                "0001 5 90",
                "0001 5 100",
                "0002 3 95",
                "0001 7 80",
                "0001 8 80",
                "0001 10 90",
                "0002 10 90",
                "0002 7 80",
                "0002 8 80",
                "0002 5 100",
                "0003 99 90"
            )
        )
            .also { it.forEach(::println) }
        println()
        solution(
            arrayOf(
                "1901 1 100",
                "1901 2 100",
                "1901 4 100",
                "1901 7 100",
                "1901 8 100",
                "1902 2 100",
                "1902 1 100",
                "1902 7 100",
                "1902 4 100",
                "1902 8 100",
                "1903 8 100",
                "1903 7 100",
                "1903 4 100",
                "1903 2 100",
                "1903 1 100",
                "2001 1 100",
                "2001 2 100",
                "2001 4 100",
                "2001 7 95",
                "2001 9 100",
                "2002 1 95",
                "2002 2 100",
                "2002 4 100",
                "2002 7 100",
                "2002 9 100"
            )
        )
            .also { it.forEach(::println) }
        println()
        solution(arrayOf("1901 10 50", "1909 10 50"))
            .also { it.forEach(::println) }
    }
}

class Solution {
    fun solution(logs: Array<String>): Array<String> {
        val answer = mutableSetOf<String>()
        val applicant = mutableMapOf<String, LinkedList<Pair<Int, Int>>>()
        val nameToScoreCount = mutableMapOf<String, Int>()

        for (log in logs) {
            val splitString = log.split(" ")
            val name = splitString[NAME]
            val number = splitString[NUMBER].toInt()
            val score = splitString[SCORE].toInt()


            if (applicant[name] == null) {
                applicant[name] = LinkedList()
            }

            applicant[name]?.removeNumberIfContains(number)
            applicant[name]?.add(number to score)
            nameToScoreCount[name] = applicant[name]!!.size
        }

        nameToScoreCount.forEach { name ->
            applicant[name.key]?.sortBy { it.first }
        }

        nameToScoreCount.keys.forEach { first ->
            nameToScoreCount.keys.forEach { second ->
                if (first != second && applicant[first]!!.size == applicant[second]!!.size && applicant[first]!!.size >= 5) {
                    applicant[first]?.containsAll(applicant[second]!!)?.takeIf { it }?.let {
                        answer.add(first)
                    }
                }
            }
        }
        val result = answer.toTypedArray()
        return if (result.isEmpty()) {
            arrayOf("None")
        } else {
            result
        }

    }

    private fun LinkedList<Pair<Int, Int>>.removeNumberIfContains(number: Int) =
        firstOrNull { it.first == number }
            ?.let { remove(it) }


    companion object {
        const val NAME = 0
        const val NUMBER = 1
        const val SCORE = 2
    }
}


/**
0001 3 95
0001 5 100
0001 5 90
0002 3 95
0001 7 80
0001 8 80
0001 10 90
0002 10 90
0002 7 80
0002 8 80
0002 5 100
0003 99 90
 */


interface MySimpleApi {
    @TestEvent("firstTest")
    fun callFirstApi(@Body("FirstSession") session: String): RequestBody

    @TestEvent("secondTest")
    fun callSecondApi(@Body("SecondSession") session: String): RequestBody
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class TestEvent(val namespace: String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Body(val value: String)
