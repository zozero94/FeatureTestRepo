package design.abstractfactory

interface StarCraftUnit {
    fun attack()
}

interface TerranUnit : StarCraftUnit
interface ProtossUnit : StarCraftUnit

class Zealot : ProtossUnit {
    override fun attack() {
        println("땈땈")
    }
}

class Dragon : ProtossUnit {
    override fun attack() {
        println("니조랄")
    }
}

class DarkTemplar : ProtossUnit {
    override fun attack() {
        println("스컹스컹")
    }
}

class HighTemplar : ProtossUnit {
    override fun attack() {
        println("지직지직")
    }
}

class Marine :TerranUnit{
    override fun attack() {
        println("투둥 투둥")
    }
}

class Firebat :TerranUnit{
    override fun attack() {
        println("빨간바지")
    }
}

class Medic:TerranUnit{
    override fun attack() {
        println("아앙...❤")
    }
}

class Ghost:TerranUnit{
    override fun attack() {
        println("수레기")
    }
}
