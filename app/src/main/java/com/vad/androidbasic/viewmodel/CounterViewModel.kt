package com.vad.androidbasic.viewmodel

import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vad.androidbasic.model.Counter
import com.vad.androidbasic.model.DataInterface

class CounterViewModel(private val dataModel: DataInterface) : ViewModel()  {
    var currentCounter: Counter? = null
        private set
    private val _value = MutableLiveData<Int>(0)
    val value : LiveData<Int> = _value

    fun updateCurrentId(id: String?) {
        val items = dataModel.items.value ?: emptyList()
        currentCounter = items.firstOrNull { it.id == id }
        _value.value = (currentCounter?.value ?: 0)
    }

    private fun plus(value: Int) {
        val currentValue = _value.value ?: 0
        _value.value = currentValue + value
    }

    fun plusOne() = plus(1)

    fun plusTwo() = plus(2)

    fun saveOrUpdate(callBack: (isSuccess: Boolean) -> Unit) {
        val tempCounter = currentCounter
        val value = _value.value ?: 0
        val counter = tempCounter?.copy(value = value) ?: Counter(
            value = value,
            dateInMillis = System.currentTimeMillis()
        )
        dataModel.addOrUpdateItem(counter)
        callBack(true)
    }
}