package com.vad.androidbasic.fragments

import androidx.recyclerview.widget.DiffUtil
import com.vad.androidbasic.model.Counter

class CounterDiffUtil(private val oldItems: List<Counter>, private val newItems: List<Counter>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldItems[oldItemPosition].id == newItems[newItemPosition].id

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldItems[oldItemPosition].isTheSameContent(newItems[newItemPosition])
}