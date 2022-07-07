package item01

// 가변성을 제한하라

import kotlin.concurrent.thread

// 가변성
fun main() {

    // var, mutable 객체를 사용하면 상태를 가질 수 있다.
    var a = 10
    var list: MutableList<Int> = mutableListOf()

    // 요소가 상태를 갖게 되면 요소의 동작은 사용 방법뿐만 아니라 이력(history)에도 의존하게 된다.
    val account = BankAccount()
    println(account.balance)
    account.deposit(100.0)
    println(account.balance)
    account.withdraw(50.0)
    println(account.balance)
    // 상태를 가지면 시간의 변화에 따라서 변하는 요소를 표현할 수 있지만 적절하게 관리하는 것이 어렵다.

    // 공유 상태를 관리하는 것은 매우 어렵다. 멀티 스레드 환경 - 충돌 발생, 동기화 필요
    var num = 0
    for (i in 1..1000) {
        thread {
            Thread.sleep(10)
            num += 1
        }
    }
    Thread.sleep(5000)
    println(num) // 매 실행마다 결과가 달라진다.

    // 동기화 코드
    /*val lock = Any()
    var num = 0
    for (i in 1..1000) {
        thread {
            Thread.sleep(10)
            synchronized(lock) {
                num += 1
            }
        }
    }
    Thread.sleep(1000)
    println(num)*/

    // 가변성은 상태를 나타내기 위한 중요한 방법이지만 변경이 일어나야 하는 부분을 신중하고 확실하게 결정하고 사용해야 한다.
}

class BankAccount {
    var balance = 0.0
        private set

    // 입금
    fun deposit(depositAmount: Double) {
        balance += depositAmount
    }

    // 출금
    @Throws(InsufficientFunds::class)
    fun withdraw(withdrawAmount: Double) {
        if (balance < withdrawAmount) {
            throw InsufficientFunds()
        }

        balance -= withdrawAmount
    }
}

class InsufficientFunds : Exception()
