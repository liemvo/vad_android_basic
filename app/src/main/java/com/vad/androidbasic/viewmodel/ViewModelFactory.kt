package com.vad.androidbasic.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

inline fun <reified T : ViewModel> Fragment.createViewModel(
    owner: ViewModelStoreOwner = this,
    noinline factory: () -> T
): T {
    val viewModelFactory = createViewModelFactory(factory)
    return ViewModelProvider(owner, viewModelFactory).get(T::class.java)
}

fun createViewModelFactory(factory: () -> ViewModel) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return factory() as T
        }
    }