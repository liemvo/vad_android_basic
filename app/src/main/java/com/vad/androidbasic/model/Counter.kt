package com.vad.androidbasic.model
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*
@Entity(tableName = "counterTable")
@Parcelize
data class Counter(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val value: Int,
    val dateInMillis: Long
): Parcelable {
    override fun equals(other: Any?): Boolean {
        return other is Counter && other.id == id
    }

    fun isTheSameContent(other: Any?): Boolean {
        return other is Counter && other.id == id && other.value == value
    }
}
