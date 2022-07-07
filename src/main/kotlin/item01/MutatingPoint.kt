package item01

import kotlin.properties.Delegates

// 다른 종류의 변경 가능 지점
fun main() {

    // 변경할 수 있는 리스트 만들기
    val list1: MutableList<Int> = mutableListOf()
    var list2: List<Int> = listOf()

    list1.add(1)
    list2 = list2 + 1
    println(list1)
    println(list2)

    list1 += 2
    list2 += 2
    println(list1)
    println(list2)

    // list1은 변경 가능 지점이 리스트 구현 내부에 존재
    // list2는 변경 가능 지점이 프로퍼티 자체가 된다.

    // 두 번째 방법의 경우 변경을 추적할 수 있다.
    val changeObserveSample = ChangeObserveSample()
    for (i in 1..5) {
        changeObserveSample.add(i)
    }
    // mutable 컬렉션을 관찰 가능하게 만드려면 추가 구현이 필요하다.


}

class ChangeObserveSample {

    var intsDelegate by Delegates.observable(listOf<Int>()) { _, old, new ->
        println("old: $old, new: $new")
    }

    var intsCustomSetter = listOf<Int>()
        set(value) {
            println("old: $field, new: $value")
            field = value
        }

    // mutable 프로퍼티에 읽기 전용 컬렉션을 넣어 사용하면 여러 객체를 변경하는 여러 메소드 대신 세터 사용 가능.
    // 세터를 private으로 만들 수도 있다.
    // 객체 변경을 제어하기 더 쉬움.

    fun add(n: Int) {
        intsDelegate = intsDelegate + n
        intsCustomSetter = intsCustomSetter + n
    }
}
