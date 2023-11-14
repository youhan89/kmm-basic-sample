package com.jetbrains.kmm.shared

class Calculator {

    enum class Operation {
        Addition, Subtraction, Multiplication, Division
    }

    sealed class Status {
        object Loading : Status()
        data class Error(val message: String) : Status()
    }

    companion object {
        fun sum(a: Int, b: Int): Int = a + b

        fun operation(): Operation = Operation.Addition

        fun getStatus(): Status =  Status.Error("Hello, Kotlin!")

        fun someDefaultedOperation(param: Int = 3) {

        }

        suspend fun send(message: String) {

        }

        fun conflicting(a: Int): String = "Did call INT version"
        fun conflicting(a: String): String = "Did call STRING version"
    }
}