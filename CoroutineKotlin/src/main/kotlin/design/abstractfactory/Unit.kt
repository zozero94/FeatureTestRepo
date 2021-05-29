package design.abstractfactory

interface Unit {
    fun attack()
}

interface Terran : Unit
interface Protoss : Unit

class Zealot : Protoss {
    override fun attack() {
        println("땈땈")
    }
}

class Dragon : Protoss {
    override fun attack() {
        println("니조랄")
    }
}

class DarkTemplar : Protoss {
    override fun attack() {
        println("스컹스컹")
    }
}

class HighTemplar : Protoss {
    override fun attack() {
        println("지직지직")
    }
}

class Marine :Terran{
    override fun attack() {
        println("투둥 투둥")
    }
}

class Firebat :Terran{
    override fun attack() {
        println("빨간바지")
    }
}

class Medic:Terran{
    override fun attack() {
        println("아앙...❤")
    }
}

class Ghost:Terran{
    override fun attack() {
        println("수레기")
    }

}
