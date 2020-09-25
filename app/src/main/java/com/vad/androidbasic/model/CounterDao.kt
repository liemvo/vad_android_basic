package com.vad.androidbasic.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CounterDao {
    @Insert
    fun insert(counter: Counter)

    @Update
    fun update(counter: Counter)

    @Delete
    fun deleteCounters(vararg counter: Counter)

    @Query("SELECT EXISTS(Select * from counterTable Where id == :counterId)")
    fun isExist(counterId: String): Boolean

    @Query("SELECT * FROM counterTable ORDER BY value asc")
    fun getAllCounters(): LiveData<List<Counter>?>
}
