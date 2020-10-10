import java.util.*

val searchList = listOf(listOf(13 to 15), listOf(0 to 7), listOf(0 to 1, 2 to 3, 4 to 5, 6 to 7))

const val text = "20202020"
//const val text = "이 시각 주요 뉴스 알려줄게요. "

fun highLightText(highLightIndex: List<Pair<Int, Int>>) {
    highLightIndex.forEach { highLightPair ->
        val preserveTarget = findPreserveText(highLightPair)
        if (preserveTarget != null) {
            val dividedList = divideHighLight(highLightPair, preserveTarget)
            dividedList.forEach(::println)
        } else {
            highLight(highLightPair)
        }
    }
}

private fun findPreserveText(highLightPair: Pair<Int, Int>) =
    searchList[2].filter {
        val highLightRange = highLightPair.first..highLightPair.second
        val searchRange = it.first..it.second
        it.first in highLightRange || it.second in highLightRange || highLightRange.first in searchRange || highLightPair.second in searchRange
    }.takeIf { it.isNotEmpty() }


private fun divideHighLight(
    highLightPair: Pair<Int, Int>,
    preserveTarget: List<Pair<Int, Int>>
): List<Pair<Int, Int>> {
    val dividedHighLightList = LinkedList<Pair<Int, Int>>()
    var previousPair = highLightPair

    for (target in preserveTarget) {
        val dividedItem = if (previousPair.first < target.first) previousPair.first to target.first else null
        if (dividedItem != null) dividedHighLightList.add(dividedItem)
        previousPair = target.second to previousPair.second
    }

    if (previousPair.first < highLightPair.second) {
        dividedHighLightList.add(previousPair.first to highLightPair.second)
    }

    return dividedHighLightList
}

private fun highLight(index: Pair<Int, Int>) {
    println("$index : ${text.substring(index.first until index.second)}")
}