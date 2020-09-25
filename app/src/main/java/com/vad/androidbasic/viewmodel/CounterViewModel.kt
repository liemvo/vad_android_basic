package com.vad.androidbasic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vad.androidbasic.model.Counter
import com.vad.androidbasic.model.DataInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CounterViewModel(private val dataModel: DataInterface) : ViewModel()  {
    var currentCounter: Counter? = null
        private set
    private val _value = MutableLiveData(0)
    val value : LiveData<Int> = _value

    fun updateCurrentCounter(counter: Counter?) {
        currentCounter = counter
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

        viewModelScope.launch(Dispatchers.IO) {
            dataModel.addOrUpdateItem(counter)
            callBack(true)
        }
    }
}