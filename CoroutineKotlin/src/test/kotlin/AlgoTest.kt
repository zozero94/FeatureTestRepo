import org.junit.Assert
import org.junit.Test

class AlgoTest {
    /**
     * 제목 : 카디날리티 소팅(바이너리)
    n개 주어진 숫자
    1 ≤ n ≤ 10^5
    n[10^6]
    주어진 숫자를 2진수 → 1의 갯수가 많은 순서대로 소팅
    같은숫자 → 10진수가 큰 숫자를 두 번째 소팅
    1→ 1
    1,2,3,4,5
    001, 010, 011, 100, 101
    → 001(1), 010(2), 100(4), 011(3), 101(5)
    →
     */
    @Test
    fun sortTest() {
        assert("01101".findOneCount() == 3)
        assert("011011".findOneCount() == 4)
        assert("001101101".findOneCount() == 5)
        assert("111101101".findOneCount() == 7)
        assert("0".findOneCount() == 0)
        assert("1".findOneCount() == 1)
    }

    @Test
    fun realSortTest() {
        val result = sort(intArrayOf(1, 2, 3, 4, 5))
        val result2 = sort(intArrayOf(5, 4, 3, 2, 1))
        Assert.assertArrayEquals(result, intArrayOf(1, 2, 4, 3, 5))
        Assert.assertArrayEquals(result2, intArrayOf(1, 2, 4, 3, 5))
    }
}

fun sort(data: IntArray): IntArray {
    val sorted = data
        .map { Integer.toBinaryString(it) to it }
        .sortedWith { o1, o2 ->
            val result = o1.first.findOneCount().compareTo(o2.first.findOneCount())
            return@sortedWith if (result == 0) o1.second.compareTo(o2.second) else result
        }

    return sorted.map { it.second }.toIntArray()
}

fun String.findOneCount(): Int {
    var count = 0
    forEach {
        if (it == '1') count++
    }
    return count
}