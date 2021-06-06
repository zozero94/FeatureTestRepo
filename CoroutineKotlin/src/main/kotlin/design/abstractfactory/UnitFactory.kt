package design.abstractfactory

import kotlin.reflect.KClass
import kotlin.reflect.full.superclasses

abstract class UnitFactory {
    abstract fun <T : StarCraftUnit> createUnit(kClass: KClass<T>): StarCraftUnit

    companion object {
        inline fun <reified T : StarCraftUnit> createInstance(): StarCraftUnit {
            return createInstance(T::class)
        }

        fun <T : StarCraftUnit> createInstance(kClass: KClass<T>): StarCraftUnit {
            kClass.superclasses.forEach { superClass ->
                return when (superClass) {
                    TerranUnit.BarrackUnit::class -> {
                        Barrack().createUnit(kClass)
                    }
                    ProtossUnit.GateWayUnit::class -> {
                        GateWay().createUnit(kClass)
                    }
                    else -> throw IllegalStateException("없는 구조물 입니다. $superClass")
                }
            }
            throw IllegalStateException("존재하지 않는 클래스타입 입니다. $kClass")
        }
    }
}

class Barrack : UnitFactory() {
    override fun <T : StarCraftUnit> createUnit(kClass: KClass<T>): TerranUnit {
        return when (kClass) {
            TerranUnit.BarrackUnit.Marine::class -> TerranUnit.BarrackUnit.Marine()
            TerranUnit.BarrackUnit.Firebat::class -> TerranUnit.BarrackUnit.Firebat()
            TerranUnit.BarrackUnit.Medic::class -> TerranUnit.BarrackUnit.Medic()
            TerranUnit.BarrackUnit.Ghost::class -> TerranUnit.BarrackUnit.Ghost()
            else -> throw IllegalStateException("없는 유닛입니다. $kClass")
        }
    }
}

class GateWay : UnitFactory() {
    override fun <T : StarCraftUnit> createUnit(kClass: KClass<T>): ProtossUnit {
        return when (kClass) {
            ProtossUnit.GateWayUnit.Zealot::class -> ProtossUnit.GateWayUnit.Zealot()
            ProtossUnit.GateWayUnit.Dragon::class -> ProtossUnit.GateWayUnit.Dragon()
            ProtossUnit.GateWayUnit.HighTemplar::class -> ProtossUnit.GateWayUnit.HighTemplar()
            ProtossUnit.GateWayUnit.DarkTemplar::class -> ProtossUnit.GateWayUnit.DarkTemplar()
            else -> throw IllegalStateException("없는 유닛입니다. $kClass")

        }
    }
}


