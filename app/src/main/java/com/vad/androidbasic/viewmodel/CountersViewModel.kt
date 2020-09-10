package com.vad.androidbasic.viewmodel

import androidx.lifecycle.ViewModel
import com.vad.androidbasic.model.DataInterface

class CountersViewModel(private val dataInterface: DataInterface) : ViewModel() {

    val items get() = dataInterface.items
    fun observe(callback: (update: Boolean) -> Unit) {
        dataInterface.neeUpdate = callback
    }
}