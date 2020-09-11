package com.vad.androidbasic.viewmodel

import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.vad.androidbasic.model.Counter
import com.vad.androidbasic.model.DataInterface

class CounterViewModel(private val dataModel: DataInterface) : ViewModel()  {
    var currentCounter: Counter? = null
        private set
    val value = ObservableInt(0)

    fun updateCurrentId(id: String?) {
        currentCounter = dataModel.items.firstOrNull { it.id == id }
        value.set(currentCounter?.value ?: 0)
    }

    private fun plus(value: Int) {
        this.value.set(this.value.get() + value)
    }

    fun plusOne() = plus(1)

    fun plusTwo() = plus(2)

    fun saveOrUpdate(callBack: (isSuccess: Boolean) -> Unit) {
        val tempCounter = currentCounter
        val value = value.get()
        val counter = tempCounter?.copy(value = value) ?: Counter(
            value = value,
            dateInMillis = System.currentTimeMillis()
        )
        dataModel.addOrUpdateItem(counter)
        callBack(true)
    }
}