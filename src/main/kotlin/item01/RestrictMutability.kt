package item01

// 코틀린에서 가변성 제한하기
fun main() {

    // 1. 읽기 전용 프로퍼티
    val num = 20
    // num = 10 컴파일 에러 - 한 번 값을 할당하면 변경할 수 없다.

    // 읽기 전용 프로퍼티는 값(value)처럼 동작하고 일반적인 방법으론 값이 변하지 않는다.
    // 하지만 읽기 전용 프로퍼티가 완전히 변경 불가능한 것은 아니다.

    // 읽기 전용 프로퍼티가 mutable 객체를 참조하고 있다면 내부적으로 변경 가능하다.
    // 읽기 전용 속성과 가변성을 구분해서 생각하자.
    val listRef = mutableListOf<Int>(1, 2, 3)
    listRef.add(4)
    println(listRef)

    // 읽기 전용 프로퍼티가 다른 프로퍼티를 활용하는 사용자 정의 게터로 정의된 경우
    val useVariable = UseVariable()
    println(useVariable.valNum)
    useVariable.varNum += 30
    println(useVariable.valNum)

    // 읽기 전용 프로퍼티의 값은 변경될 수 있기는 하지만 프로퍼티 참조 자체를 변경할 수는 없기 때문에 동기화 문제 등을 줄일 수 있다.
    // 일반적으로 val을 사용하는 이유

    // 2. 가변 컬렉션과 읽기 전용 컬렉션 구분하기
    // 코틀린의 컬렉션 계층은 읽기 전용 컬렉션과 가변 컬렉션을 구분한다.
    // Mutable이 붙은 가변 컬렉션은 대응되는 읽기 전용 컬렉션 인터페이스를 상속 받아서 변경을 위한 메소드를 추가한 것이다.

    val mutableList = mutableListOf(1, 2, 3)
    val immutableList = listOf(1, 2, 3)
    mutableList.add(4)
    println(mutableList)
    // list.add(4) 컴파일 에러
    println(immutableList)

    // 읽기 전용 컬렉션이 내부의 값을 변경할 수 없다는 의미는 아니다. 하지만 읽기 전용 인터페이스가 이를 지원하지 않는다.
    // Iterable<T>.map은 ArrayList를 리턴한다. ArrayList는 변경 가능한 리스트지만, Iterable<T>.map의 반환 타입이 List<T>이다.
    // 컬렉션을 진짜 불변으로 만드는 것이 아니고 읽기 전용 인터페이스를 사용해서 불변으로 만든다.

    // 다운 캐스팅 절대 금지!
    val list = listOf(1, 2, 3)
    println(list is MutableList) // true
    // listOf는 자바의 List 인터페이스를 구현한 ArrayList의 인스턴스를 리턴한다. 자바의 List 인터페이스는 add와 set을 구현하고 있다.

    // 이를 코틀린의 MutableList로 변경할 수 있다. -> 다운 캐스팅
    if (list is MutableList) { // 다운 캐스팅
        list.add(4) // 컴파일 에러
    }
    // 하지만 코틀린의 List 인터페이스는 이러한 연산을 지원하지 않는다.

    // 복제를 통해 새로운 Mutable 컬렉션을 만들어서 사용해야 한다.
    val listToMutableList = list.toMutableList()
    listToMutableList.add(4)
    println(listToMutableList)

    // 3. 데이터 클래스의 copy
    // 불변 객체는 변경할 수 없다.
    // 변경을 위해선 자신의 일부를 수정한 새로운 객체를 만들어 리턴하는 메소드를 가져야 한다.
    var user = User("Lee", "qwerty@test.com")
    user = user.withEmail("zxcv@test.com")
    println(user)

    // data class를 사용하면 copy 메소드를 만들어준다.
    // copy 메소드를 사용하면 모든 기본 생성자 프로퍼티가 같은 새로운 객체를 만들어 낼 수 있다.
    var userData = UserData("Lee", "zxcv1234@test.com")
    userData = userData.copy(email = "qwerty@test.com")
    println(userData)
}

class UseVariable {
    var varNum = 20
    val valNum
        get() = varNum // 값에 접근할 때마다 사용자 정의 게터 호출
}

class User(val name: String, val email: String) {
    fun withEmail(email: String) = User(name, email)
}

data class UserData(val name: String, val email: String)