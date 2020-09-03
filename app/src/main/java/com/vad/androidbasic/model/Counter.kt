package com.vad.androidbasic.model
import java.util.*

data class Counter(
    val id: String = UUID.randomUUID().toString(),
    val value: Int,
    val dateInMillis: Long
) {
    override fun equals(other: Any?): Boolean {
        return other is Counter && other.id == id
    }

    fun isTheSameContent(other: Any?): Boolean {
        return other is Counter && other.id == id && other.value == value
    }
}

internal val currentTime by lazy { System.currentTimeMillis() }
internal const val ONE_DAY_MILLIS = 86_400_000
