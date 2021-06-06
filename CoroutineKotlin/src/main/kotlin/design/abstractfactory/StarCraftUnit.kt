package design.abstractfactory

abstract class StarCraftUnit {
    abstract val mineral: Int
    abstract val gas: Int
    abstract fun attack()
}

abstract class TerranUnit : StarCraftUnit() {

    sealed class BarrackUnit : TerranUnit() {
        class Marine(override val mineral: Int = 50, override val gas: Int = 0) : BarrackUnit() {
            override fun attack() {
                println("$this : 투둥 투둥 $mineral/$gas")
            }
        }

        class Firebat(override val mineral: Int = 50, override val gas: Int = 25) : BarrackUnit() {
            override fun attack() {
                println("$this 빨간바지 $mineral/$gas")
            }
        }

        class Medic(override val mineral: Int = 50, override val gas: Int = 25) : BarrackUnit() {
            override fun attack() {
                println("$this 아앙...❤ $mineral/$gas")
            }
        }

        class Ghost(override val mineral: Int = 25, override val gas: Int = 75) : BarrackUnit() {
            override fun attack() {
                println("$this 수레기 $mineral/$gas")
            }
        }
    }

}

abstract class ProtossUnit : StarCraftUnit() {

    sealed class GateWayUnit : ProtossUnit() {
        class Zealot(override val mineral: Int = 100, override val gas: Int = 0) : GateWayUnit() {
            override fun attack() {
                println("$this 땈땈 $mineral/$gas")
            }
        }

        class Dragon(override val mineral: Int = 125, override val gas: Int = 50) : GateWayUnit() {
            override fun attack() {
                println("$this 니조랄 $mineral/$gas")
            }
        }

        class DarkTemplar(override val mineral: Int = 125, override val gas: Int = 100) : GateWayUnit() {
            override fun attack() {
                println("$this 스컹스컹 $mineral/$gas")
            }
        }

        class HighTemplar(override val mineral: Int = 50, override val gas: Int = 150) : GateWayUnit() {
            override fun attack() {
                println("$this 지직지직 $mineral/$gas")
            }
        }
    }
}
