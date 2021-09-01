fun main() {
    example()
    println("--------------------------------")
    retainTest()
}

fun example() {
    val curr = listOf(1, 2, 3, 4)
    val prev = listOf(1, 2, 5)

    curr.intersect(prev).also { println(it) }//겹치는 부분을 추출
    curr.subtract(prev).also { println(it) }//다른 부분을 추출
    curr.union(prev).also { println(it) }//두개를 합친 결과를 추출
}

fun retainTest() {
    val list = mutableListOf(1, 2, 3, 4, 5)
    val compareList = mutableListOf(1, 3, 6)
    list.retainAll(compareList) // 현재 list 에서 비교 대상과 겹치는 부분만 남겨둠
    println(list)
}