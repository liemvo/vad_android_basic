package com.vad.androidbasic.di

import android.content.Context
import androidx.room.Room
import com.vad.androidbasic.model.CounterDao
import com.vad.androidbasic.model.CounterDatabase
import com.vad.androidbasic.model.DataInterface
import com.vad.androidbasic.model.RoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CounterDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            CounterDatabase::class.java,
            CounterDatabase.databaseName
        ).build()
    
    @Provides
    @Singleton
    fun provideCounterDao(counterDatabase: CounterDatabase): CounterDao =
        counterDatabase.counterDao()
    
    @Provides
    @Singleton
    fun profileDataInterface(roomRepository: RoomRepository): DataInterface = roomRepository
}
