package design.abstractfactory

abstract class UnitFactory {
    abstract fun createUnit(unit: StarCraftUnit): StarCraftUnit

    companion object {
        fun createInstance(type: StarCraftUnit): StarCraftUnit {
            return when (type) {
                is TerranUnit -> Barrack().createUnit(type)
                is ProtossUnit -> GateWay().createUnit(type)
                else -> throw IllegalStateException("없는 종족입니다.")
            }
        }
    }
}

internal class Barrack : UnitFactory() {
    override fun createUnit(unit: StarCraftUnit): TerranUnit {
        check(unit is TerranUnit) { "테란 유닛이 아닙니다." }

        return when (unit) {
            is TerranUnit.BarrackUnit.Marine -> TerranUnit.BarrackUnit.Marine()
            is TerranUnit.BarrackUnit.Firebat -> TerranUnit.BarrackUnit.Firebat()
            is TerranUnit.BarrackUnit.Medic -> TerranUnit.BarrackUnit.Medic()
            is TerranUnit.BarrackUnit.Ghost -> TerranUnit.BarrackUnit.Ghost()
            else -> throw IllegalStateException("없는 유닛입니다.")
        }
    }
}

internal class GateWay : UnitFactory() {
    override fun createUnit(unit: StarCraftUnit): ProtossUnit {
        check(unit is ProtossUnit) { "프로토스 유닛이 아닙니다." }
        return when (unit) {
            is ProtossUnit.GateWayUnit.Zealot -> ProtossUnit.GateWayUnit.Zealot()
            is ProtossUnit.GateWayUnit.Dragon -> ProtossUnit.GateWayUnit.Dragon()
            is ProtossUnit.GateWayUnit.HighTemplar -> ProtossUnit.GateWayUnit.HighTemplar()
            is ProtossUnit.GateWayUnit.DarkTemplar -> ProtossUnit.GateWayUnit.DarkTemplar()
            else -> throw IllegalStateException("없는 유닛입니다.")

        }
    }
}


