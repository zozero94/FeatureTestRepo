fun main() {
    /**
     * Example Case 1
     * text = "이 시각 주요 뉴스 알려줄게요. "
     */

    /**
     * highLight : "알려줄게요"
     * search : "줄게"
     */
    highLightText(listOf(11 to 18), listOf(13 to 15))
    println("---------------------------------------------")
    /**
     * highLight : "시각"
     * search : "이 시각 주요"
     */
    highLightText(listOf(2 to 4), listOf(0 to 7))
    println("---------------------------------------------")

    /**
     * Example Case 2
     * text = "20202020"
     */

    /**
     * highLight : "20202020"
     * search : "2"
     */
    highLightText(listOf(0 to 8), listOf(0 to 1, 2 to 3, 4 to 5, 6 to 7))
}

//const val text = "20202020"

const val text = "이 시각 주요 뉴스 알려줄게요. "
fun highLightText(highLightIndex: List<Pair<Int, Int>>, searchList: List<Pair<Int, Int>>) {
    highLightIndex.forEach { highLightPair ->
        val preserveTarget = findPreserveText(highLightPair, searchList)
        val isMultiText = !preserveTarget.isNullOrEmpty()

        if (preserveTarget != null) {
            var tempPair = highLightPair
            preserveTarget.forEach {
                tempPair = divideHighLight(tempPair, it, isMultiText) ?: highLightPair
            }
        } else {
            highLight(highLightPair)
        }
    }
}

private fun findPreserveText(
    highLightPair: Pair<Int, Int>,
    searchList: List<Pair<Int, Int>>
) =
    searchList.filter {
        val highLightRange = highLightPair.first..highLightPair.second
        val searchRange = it.first..it.second
        it.first in highLightRange || it.second in highLightRange || highLightRange.first in searchRange || highLightPair.second in searchRange
    }.takeIf { it.isNotEmpty() }


private fun divideHighLight(
    highLightPair: Pair<Int, Int>,
    preserveTarget: Pair<Int, Int>,
    isMultiText: Boolean
): Pair<Int, Int>? {

    val frontIndex =
        if (highLightPair.first < preserveTarget.first) highLightPair.first to preserveTarget.first else null
    if (frontIndex != null) {
        highLight(frontIndex)
    }

    val backIndex =
        if (preserveTarget.second < highLightPair.second)
            preserveTarget.second to highLightPair.second
        else null
//    println("back $backIndex")
    if (backIndex != null && !isMultiText) {
        highLight(backIndex)
    }

    return backIndex
}

private fun highLight(index: Pair<Int, Int>) {
    println("$index : ${text.substring(index.first until index.second)}")
}