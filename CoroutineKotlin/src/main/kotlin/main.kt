fun main() {
    var estimatedTime=0
    val time =3600001L
    if (time > 3600000L) println(estimatedTime)
    for (i in 0 until time step 360000) { // 6분간격으로 시간을 나눈다.
        if (estimatedTime >= 10) break
        estimatedTime++
    }
    print(estimatedTime)
}