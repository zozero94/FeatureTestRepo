fun main() {
    val concreteClass = ConcreteClass()
    val delegation = Delegation(concreteClass)
    delegation.doSomeThing()

    println("---------------------------------")
    val delegationKotlin = DelegationKotlin(concreteClass)
    delegationKotlin.doSomeThing()
}

interface Component {
    fun doSomeThing()
}

class ConcreteClass : Component {

    override fun doSomeThing() {
        println("이거시 바로 원래 메소드 호출이여라.")
    }
}


class Delegation(private val origin: Component) : Component {

    override fun doSomeThing() {
        println("이거슨 데코레이터에서 호출되는 것이란다.")
        origin.doSomeThing()
    }
}

class DelegationKotlin(private val origin: Component) : Component by origin {
    // Component 의 기능을 오버라이드 하지 않아도, 바이패스는 자동지원한다.
}
