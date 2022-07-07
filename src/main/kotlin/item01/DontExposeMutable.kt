package item01

// 변경 가능 지점 노출하지 말기
fun main() {

    val studentHolder = StudentHolder()

    // _students 참조를 리턴하는 사용자 정의 게터를 사용한 경우
    println(studentHolder.students)
    val temp1 = studentHolder.students as MutableList<Student> // 변경 가능한 컬렉션으로 캐스팅
    temp1.add(Student(1055, "Park"))
    println(temp1)
    println(studentHolder.students) // private인 원본이 외부에 노출하지 않았지만 변경될 위험이 있음
    studentHolder.printOrigin()

    // 방어적 복사 사용
    println(studentHolder.students)
    val temp2 = studentHolder.students as MutableList<Student>
    temp2.add(Student(1060, "Choi"))
    println(temp2)
    println(studentHolder.students) // 원본이 변경되지 않음. 그럼에도 규약을 어기진 말자.
    studentHolder.printOrigin()

    println(studentHolder.students)
    val newStudent = Student(1055, "Park")
    studentHolder.addStudent(newStudent)
    println(studentHolder.students)
    studentHolder.removeStudent(newStudent)
    println(studentHolder.students)
    studentHolder.printOrigin()

    // 가능하다면 무조건 가변성을 제한하는 것이 좋다.
}

class StudentHolder {

    // mutable 객체를 외부에 노출하지 않는다.
    private val _students: MutableList<Student> = mutableListOf()
    val students: List<Student>
        // get() = _students
        get() = _students.toList()

    init {
        _students.add(Student(1021, "Lee"))
        _students.add(Student(1024, "Kim"))
        _students.add(Student(1048, "Park"))
    }

    fun addStudent(student: Student) {
        _students.add(student)
    }

    fun removeStudent(student: Student) {
        _students.remove(student)
    }

    fun printOrigin() {
        println(_students)
    }
}

data class Student(

    val id: Int,
    val name: String,
)