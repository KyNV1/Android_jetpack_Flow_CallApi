package com.example.android_jetpack_flow_callapi.learnFlow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking


fun getNumbers():Flow<Int> = flow {
    for(i in 1..3){
        delay(1000L)
        emit(i)
    }
}

fun main() {
    runBlocking {
        getNumbers().collect{value->
            println(value)
        }
    }
}