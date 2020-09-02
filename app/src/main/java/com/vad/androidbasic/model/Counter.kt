package com.vad.androidbasic.model
import java.util.UUID
import kotlin.time.ExperimentalTime

data class Counter(
    val id: String = UUID.randomUUID().toString(),
    val value: Int,
    val dateInMillis: Long
) {
    override fun equals(other: Any?): Boolean {
        return other is Counter && other.id == id
    }

    fun isTheSameContent(other: Any?): Boolean {
        return other is Counter && other.id == id && other.value == other.value
    }
}

private val currentTime by lazy { System.currentTimeMillis() }
private const val ONE_DAY_MILLIS = 86_400_000
@ExperimentalTime
fun generateFakeData()  = (0..10).toList().map {
    Counter(value = it * 10, dateInMillis = currentTime - it * ONE_DAY_MILLIS)
}.toList()