package com.vad.androidbasic.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataImplement: DataInterface {
    private val _mutableLiveData = MutableLiveData<List<Counter>?> ()
    override val items: LiveData<List<Counter>?> = _mutableLiveData
    private val _items = mutableListOf<Counter>()

    private object Holder {
        val dataController = DataImplement()
    }

    override suspend fun removeItem(counter: Counter) {
        _items.removeAll { it.id == counter.id }
    }

    override suspend fun addOrUpdateItem(counter: Counter) {
        val index = _items.indexOf(counter)
        if (index in 0.._items.size) {
            _items[index] = counter
        } else {
            _items.add(counter)
        }
        _mutableLiveData.value = _items
    }

    companion object {
        val instance = Holder.dataController
    }
}