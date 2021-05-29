package design.abstractfactory

abstract class UnitFactory {
    abstract fun createUnit(): Unit

    companion object {
        fun createGateWay() = GateWay()
        fun createBarrack() = Barrack()
    }
}

class Barrack : UnitFactory() {
    override fun createUnit(): Unit {
        TODO("Not yet implemented")
    }
}

class GateWay : UnitFactory() {
    override fun createUnit(): Unit {
        TODO("Not yet implemented")
    }
}


