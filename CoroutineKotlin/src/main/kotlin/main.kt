fun main() {

    val concreteClass = ConcreteClass()
    val delegation = Delegation(concreteClass)
    delegation.doSomeThing()

    println("---------------------------------")
    val delegationKotlin = DelegationKotlin(concreteClass)
    delegationKotlin.doSomeThing()

    println("---------------------------------")
    val proxy = Proxy()
    proxy.doSomeThing()
}

interface Component {
    fun doSomeThing()
}

class ConcreteClass : Component {

    override fun doSomeThing() {
        println("이거시 바로 원래 메소드 호출이여라.")
    }
}

/**
 * 데코레이트 패턴
 * 런타임에 구체 클래스가 지정 됨
 * @see Component
 */
class Delegation(private val origin: Component) : Component {

    override fun doSomeThing() {
        println("이거슨 데코레이터에서 호출되는 것이란다.")
        origin.doSomeThing()
    }
}

class DelegationKotlin(private val origin: Component) : Component by origin {
    // Component 의 기능을 오버라이드 하지 않아도, 바이패스는 자동지원한다.
}

/**
 * 프록시 패턴 : 레이지 하게 생성 할 수 있음
 * 컴파일 시간에 구체 클래스가 지정 됨
 * @see ConcreteClass
 */
class Proxy : Component {
    private lateinit var proxy: Component
    override fun doSomeThing() {
        proxy = ConcreteClass()
        println("이거슨 프록시에서 호출되는 것이란다.")
        proxy.doSomeThing()
    }

}
