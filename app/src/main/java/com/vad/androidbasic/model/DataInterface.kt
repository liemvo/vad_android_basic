package com.vad.androidbasic.model

import androidx.lifecycle.LiveData

interface DataInterface {
    val items: LiveData<List<Counter>?>
    suspend fun addOrUpdateItem(counter: Counter)
    suspend fun removeItem(counter: Counter)
}
