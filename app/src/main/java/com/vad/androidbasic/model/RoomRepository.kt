package com.vad.androidbasic.model

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomRepository @Inject constructor(
    private val counterDao: CounterDao
) : DataInterface {
    
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