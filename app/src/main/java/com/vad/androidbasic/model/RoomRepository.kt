package com.vad.androidbasic.model

import androidx.lifecycle.LiveData
import com.vad.androidbasic.CounterApplication

class RoomRepository : DataInterface {
    private val counterDao: CounterDao = CounterApplication.database.counterDao()

    override val items: LiveData<List<Counter>?>
        get() = counterDao.getAllCounters()

    override suspend fun addOrUpdateItem(counter: Counter) {
        val isExist = counterDao.isExist(counter.id)
        if (isExist) {
            counterDao.update(counter)
        } else {
            counterDao.insert(counter)
        }
    }

    override suspend fun removeItem(counter: Counter) {
        counterDao.deleteCounters(counter)
    }
}