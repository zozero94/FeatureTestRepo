import design.abstractfactory.ProtossUnit
import design.abstractfactory.TerranUnit
import design.abstractfactory.UnitFactory
import org.junit.Test

class Factory {

    @Test
    fun MarineTest() {
        val unit = UnitFactory.createInstance<TerranUnit.BarrackUnit.Marine>()
        unit.attack()
        assert(unit is TerranUnit.BarrackUnit.Marine)
    }

    @Test
    fun ZealotTest() {
        val unit = UnitFactory.createInstance<ProtossUnit.GateWayUnit.Zealot>()
        unit.attack()
        assert(unit is ProtossUnit.GateWayUnit.Zealot)
    }

    @Test
    fun MedicTest() {
        val unit = UnitFactory.createInstance<TerranUnit.BarrackUnit.Medic>()
        unit.attack()
        assert(unit is TerranUnit.BarrackUnit.Medic)
    }

    @Test
    fun FirebatTest() {
        val unit = UnitFactory.createInstance<TerranUnit.BarrackUnit.Firebat>()
        unit.attack()
        assert(unit is TerranUnit.BarrackUnit.Firebat)
    }

}