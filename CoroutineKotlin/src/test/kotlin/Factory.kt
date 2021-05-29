import design.abstractfactory.*
import org.junit.Before
import org.junit.Test

class Factory {
    //todo 목적
    // 1. 팩토리를 통해 종족을 선택
    // 2. 유닛의 가격을 입력하면 원하는 유닛이 튀어나오도록
    private lateinit var terranFactory: UnitFactory
    private lateinit var protossFactory: UnitFactory

    @Before
    fun setUp(){
        terranFactory = UnitFactory.createInstance(UnitFactory.Type.Terran)
        protossFactory = UnitFactory.createInstance(UnitFactory.Type.Protoss)
    }
    @Test
    fun MarineTest(){
        val unit = terranFactory.createUnit(50) //마린은 50원
        unit.attack()
        assert(unit is Marine)
    }

    @Test
    fun ZealotTest(){
        val unit = protossFactory.createUnit(100) //질럿은 100원
        unit.attack()
        assert(unit is Zealot)
    }

    @Test
    fun MedicTest(){
        val unit = terranFactory.createUnit(50,25) //메딕은 50원 가스 25원
        unit.attack()
        assert(unit is Medic)//expect : Medic result : Firebat
    }
}