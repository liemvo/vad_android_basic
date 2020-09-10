package com.vad.androidbasic.model

interface DataInterface {
    val items: List<Counter>
    var neeUpdate: (willUpdate: Boolean) -> Unit
    fun addOrUpdateItem(counter: Counter)
    fun removeItem(counter: Counter)
}