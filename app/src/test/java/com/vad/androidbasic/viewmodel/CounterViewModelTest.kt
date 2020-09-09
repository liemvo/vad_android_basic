package com.vad.androidbasic.viewmodel

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.vad.androidbasic.model.Counter
import com.vad.androidbasic.model.DataInterface
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class CounterViewModelTest {
    private val dataInterface = mock(DataInterface::class.java)
    private lateinit var viewModel: CounterViewModel

    @Before
    fun setUp() {
        viewModel = CounterViewModel(dataInterface)
    }

    @Test
    fun testInitialValue() {
        viewModel.onDataUpdate {
            Assert.assertEquals(0, it)
        }
    }

    @Test
    fun testUpdateCurrent() {
        val value = 5
        val counter = Counter(value = value, dateInMillis = 32902L)
        val items = listOf(counter)
        val findId = counter.id

        whenever(dataInterface.items).doReturn(items)

        viewModel.updateCurrentId(findId)

        Assert.assertEquals(counter, viewModel.currentCounter)
    }
}