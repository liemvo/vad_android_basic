package com.vad.androidbasic

import android.app.Application
import com.vad.androidbasic.model.CounterDatabase

class CounterApplication: Application() {
    companion object {
        lateinit var database: CounterDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = CounterDatabase.getDataBase(this)
    }
}