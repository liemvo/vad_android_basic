package com.vad.androidbasic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vad.androidbasic.model.DataInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountersViewModel @Inject constructor(dataInterface: DataInterface) : ViewModel() {
    private val _navigation = MutableLiveData<SingleLiveEvent<Boolean?>>()
    val navigation: LiveData<SingleLiveEvent<Boolean?>> = _navigation
    val items = dataInterface.items

    fun onNewCounter() {
        _navigation.value = SingleLiveEvent(true)
    }
}
