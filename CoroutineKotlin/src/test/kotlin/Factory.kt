import design.abstractfactory.ProtossUnit
import design.abstractfactory.StarCraftUnit
import design.abstractfactory.TerranUnit
import design.abstractfactory.UnitFactory
import org.junit.Test

class Factory {
    //todo 목적
    // 1. 팩토리를 통해 종족을 선택
    // 2. 유닛의 가격을 입력하면 원하는 유닛이 튀어나오도록

    @Test
    fun MarineTest() {
        val unit = UnitFactory.createInstance(TerranUnit.BarrackUnit.Marine())
        unit.attack()
        assert(unit is TerranUnit.BarrackUnit.Marine)
    }

    @Test
    fun MarineTest2() {
        val unit: StarCraftUnit = TerranUnit.BarrackUnit.Marine()
        unit.attack()
        assert(unit is TerranUnit.BarrackUnit.Marine)
    }

    @Test
    fun ZealotTest() {
        val unit = UnitFactory.createInstance(ProtossUnit.GateWayUnit.Zealot())
        unit.attack()
        assert(unit is ProtossUnit.GateWayUnit.Zealot)
    }

    @Test
    fun MedicTest() {
        val unit = UnitFactory.createInstance(TerranUnit.BarrackUnit.Medic())
        unit.attack()
        assert(unit is TerranUnit.BarrackUnit.Medic)
    }

    @Test
    fun FirebatTest() {
        val unit = UnitFactory.createInstance(TerranUnit.BarrackUnit.Firebat())
        unit.attack()
        assert(unit is TerranUnit.BarrackUnit.Firebat)
    }

}