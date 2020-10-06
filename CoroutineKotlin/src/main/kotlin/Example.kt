class Solution {
    fun solution(new_id: String): String {
        // Case1 대문자 -> 소문자
        val result1 = new_id.toLowerCase()
        //정규식 1 영어, 숫자, - _ .
        val result2 = result1.replace("[^a-z0-9-_.]".toRegex(), "")
        //정규식 2 중복되는 2 이상의 . 하나의 .으로 대치
        val result3 = result2.replace("[.]{2,}".toRegex(), ".")
        //앞뒤 점 없애기
        val result4 = 앞뒤점없애기(result3)
        //받아온 결과가 빈칸이면 "a"를 돌려줌 아니면 원래 자신의 값 -> (result4=="") ? "a" : result4
        val result5 = if (result4.isBlank()) "a" else result4
        //15자 이상이면 스트링을 자른다 -> (result5.length) ? result5.substring(0,15) : result5
        val result6 = if (result5.length > 15) result5.substring(0 until 15) else result5
        //앞뒤 점 없애기
        val result7 = 앞뒤점없애기(result6)
        //길이가 2 이하면 맨 마지막 문자 추가하기
        return 마지막(result7)
    }

    private fun 앞뒤점없애기(result3: String): String {
        var result = result3
        //받아온 파라미터의 첫번째, 마지막 char을 가져온다
        val (first, last) = result3.first() to result3.last()
        //가져온 결과가 빈결과가 아니고 '.' 이라면 제거
        if (result.isNotBlank() && first == '.') {
            result = result.removeRange(0..0)
        }
        //가져온 결과가 빈결과가 아니고 '.' 이라면 제거
        if (result.isNotBlank() && last == '.') {
            result = result.removeRange(result.lastIndex..result.lastIndex)
        }
        return result
    }

    private fun 마지막(result7: String): String {
        var result = result7
        if (result7.length <= 2) {
            val last = result7.last()
            while (result.length != 3) {
                result += last
            }
        }
        return result
    }

}