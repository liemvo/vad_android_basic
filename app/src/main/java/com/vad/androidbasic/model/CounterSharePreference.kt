package com.vad.androidbasic.model

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class CounterSharePreference private constructor(context: Context): DataInterface {
    private val _mutableLiveData = MutableLiveData<List<Counter>?> ()
    override val items: LiveData<List<Counter>?> = _mutableLiveData
    private val _items = mutableListOf<Counter>()
    private var sharePreference: SharedPreferences = context.getSharedPreferences("Counter", Context.MODE_PRIVATE)
    private val gson by lazy {
        Gson()
    }

    private val tokenType by lazy {
        object : TypeToken<MutableList<Counter>>() {}.type
    }

    init {
        GlobalScope.launch {
            parserData()
        }
    }

    private suspend fun parserData() {
        sharePreference.getString(COUNTERS_KEY, null)?.let {

            try {
                _items.addAll(gson.fromJson<MutableList<Counter>>(it, tokenType))
                _mutableLiveData.postValue(_items)
            } catch (e: Exception) {

            }
        }
    }

    private suspend fun saveToReference() {
        gson.toJson(_items).also {
            sharePreference.edit().run {
                putString(COUNTERS_KEY, it)
                apply()
            }
        }
    }

    override suspend fun addOrUpdateItem(counter: Counter) {
        val index = _items.indexOf(counter)
        if (index in 0.._items.size) {
            _items[index] = counter
        } else {
            _items.add(counter)
        }
        coroutineScope { saveToReference() }
        _mutableLiveData.postValue(_items)
    }

    override suspend fun removeItem(counter: Counter) {
        _items.removeAll { it.id == counter.id }
    }

    companion object {
        private const val COUNTERS_KEY = "Counter"
        private var INSTANCE: CounterSharePreference? = null

        fun getCounterSharePreference(context: Context): CounterSharePreference {
            val instance = INSTANCE
            if (instance != null) return instance

            synchronized(this){
                return CounterSharePreference(context).apply {
                    INSTANCE = this
                }
            }
        }
    }
}
