package com.jetbrains.kmm.shared

import co.touchlab.skie.configuration.annotations.DefaultArgumentInterop
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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

        fun getStatus(): Status =  Status.Error("I'm Kotlin, please put me out of my misery!")

        fun manuallyDefaultedOperation(param: Int) {

        }

        fun manuallyDefaultedOperation() {
            manuallyDefaultedOperation(3)
        }


        @DefaultArgumentInterop.Enabled
        fun someDefaultedOperation(param: Int = 3) {

        }

        suspend fun asyncWork() = coroutineScope {
            launch {
                delay(5000)
            }
        }

        fun flowingIntegers(): Flow<Int> = flow {
            for (i in 1..10) {
                delay(1000)
                emit(i)
            }
        }

        fun conflicting(a: Int): String = "Did call INT version"
        fun conflicting(a: String): String = "Did call STRING version"
    }
}

sealed class MyCustomExceptions(message: String) : Exception(message) {
    data class InvalidSessionCookie(val msg: String): MyCustomExceptions(msg)
    data class InvalidCode(val msg: String): MyCustomExceptions(msg)
    data class UserBlacklisted(val msg: String): MyCustomExceptions(msg)
}