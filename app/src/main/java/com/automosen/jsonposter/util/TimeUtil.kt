package com.automosen.jsonposter.util

fun now() : String {
    return (System.currentTimeMillis()/1000).toString()
}
fun timediff(start_time : String, end_time : String)  : Int = end_time.toInt() - start_time.toInt()