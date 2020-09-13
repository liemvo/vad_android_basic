package com.vad.androidbasic.model

import androidx.lifecycle.LiveData

interface DataInterface {
    val items: LiveData<List<Counter>?>
    fun addOrUpdateItem(counter: Counter)
    fun removeItem(counter: Counter)
}