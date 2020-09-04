package com.vad.androidbasic.fragments

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vad.androidbasic.R
import com.vad.androidbasic.model.Counter
import kotlinx.android.synthetic.main.item.view.*

class CounterAdapter(private val callBack: (id: String) -> Unit): RecyclerView.Adapter<CounterAdapter.Companion.CounterHolder>() {
    private val counters: MutableList<Counter> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CounterHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false),
        callBack
    )

    fun updateList(data: List<Counter>) {
        val result = DiffUtil.calculateDiff(CounterDiffUtil(counters, data))
        counters.clear()
        counters.addAll(data)
        // notifyDataSetChanged()
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = counters.size

    override fun onBindViewHolder(holder: CounterHolder, position: Int) {
        holder.bind(counters[position])
    }

    fun moveItem(from: Int, to: Int) {
        val newValues = counters.swap(from, to)
        counters.clear()
        counters.addAll(newValues)
    }

    fun notifyMoveItem(from: Int, to: Int) {
        notifyItemChanged(from)
        notifyItemChanged(to)
    }

    companion object {
        class CounterHolder(
            view: View,
            val callBack: (id: String) -> Unit
        ) : RecyclerView.ViewHolder(view) {

            fun bind(counter: Counter) {
                itemView.value.text = "${counter.value}"
                itemView.date.text = itemView.context.toDate(counter.dateInMillis)
                itemView.cardItem.setOnClickListener {
                    callBack(counter.id)
                }
            }
        }
    }
}

private fun Context.toDate(dateInMillis: Long): CharSequence? {
    val flags = DateUtils.FORMAT_SHOW_DATE
    return DateUtils.formatDateTime(this, dateInMillis, flags)
}

private fun <T> Iterable<T>.swap(from: Int, to: Int): Iterable<T> {
    val fromItem: T? = find { indexOf(it) == from }
    val toItem: T? = find { indexOf(it) == to }
    return mapIndexed { i, existing -> if (i == from && toItem != null) toItem else if (i == to && fromItem != null) fromItem else existing }
}