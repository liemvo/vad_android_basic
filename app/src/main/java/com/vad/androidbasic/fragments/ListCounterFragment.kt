package com.vad.androidbasic.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.vad.androidbasic.NavigationInterface
import com.vad.androidbasic.R
import com.vad.androidbasic.model.*
import com.vad.androidbasic.model.ONE_DAY_MILLIS
import com.vad.androidbasic.model.currentTime
import kotlinx.android.synthetic.main.counters_fragment.*
import kotlin.time.ExperimentalTime

@ExperimentalTime
class ListCounterFragment: Fragment() {
    private val navigationController by lazy {
        requireActivity() as? NavigationInterface
    }

    private val adapter by lazy {
        CounterAdapter(onItemClick)
    }

    private val onItemClick: (id: String) -> Unit = { id ->
        DataImplement.instance.items.firstOrNull { it.id == id }?.let { item ->
            DataImplement.instance.addOrUpdateItem(item.copy(value = item.value + 3))
            adapter.updateList(DataImplement.instance.items)
        }
    }

    private val itemTouchHelper by lazy {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(UP or DOWN, 0) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val adapter = recyclerView.adapter as CounterAdapter
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                adapter.moveItem(from, to)
                adapter.notifyMoveItem(from, to)
                Log.e("ListCounter", "onMove")
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                Log.e("ListCounter", "onSwiped")
            }
        }
        ItemTouchHelper(simpleItemTouchCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.counters_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.adapter = adapter
        adapter.updateList(DataImplement.instance.items)
        itemTouchHelper.attachToRecyclerView(recycler)
        newCounter.setOnClickListener {
            val size = DataImplement.instance.items.size
            DataImplement.instance.addOrUpdateItem(Counter(value = size * 10  + 1, dateInMillis = currentTime - size * ONE_DAY_MILLIS))
            adapter.updateList(DataImplement.instance.items)
            // navigationController?.navigateTo(CounterFragment.newInstance())
        }
    }

    companion object {
        fun newInstance() = ListCounterFragment()
    }
}
