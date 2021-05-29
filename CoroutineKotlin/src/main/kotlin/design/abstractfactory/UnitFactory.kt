package design.abstractfactory

abstract class UnitFactory {
    sealed class Type {
        object Terran : Type()
        object Protoss : Type()
    }

    abstract fun createUnit(mineral: Int = 0, gas: Int = 0): StarCraftUnit

    companion object {
        fun createInstance(type: Type): UnitFactory {
            return when (type) {
                Type.Protoss -> GateWay()
                Type.Terran -> Barrack()
            }
        }
    }
}


class Barrack : UnitFactory() {
    override fun createUnit(mineral: Int, gas: Int): TerranUnit {
        return if (mineral == 50 && gas == 0) {
            Marine()
        } else if (mineral == 50 && gas == 25) {
            Firebat()
        } else if (mineral == 50 && gas == 25) {
            Medic()
        } else if (mineral == 25 && gas == 75) {
            Ghost()
        } else {
            throw IllegalStateException("없는 테란 유닛입니다.")
        }
    }
}

class GateWay : UnitFactory() {
    override fun createUnit(mineral: Int, gas: Int): ProtossUnit {
        return if (mineral == 100 && gas == 0) {
            Zealot()
        } else if (mineral == 125 && gas == 50) {
            Dragon()
        } else if (mineral == 50 && gas == 150) {
            HighTemplar()
        } else if (mineral == 125 && gas == 100) {
            DarkTemplar()
        } else {
            throw IllegalStateException("없는 프로토스 유닛입니다.")
        }
    }
}


