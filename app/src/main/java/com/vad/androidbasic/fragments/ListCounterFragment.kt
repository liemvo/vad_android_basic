package com.vad.androidbasic.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.RecyclerView
import com.vad.androidbasic.databinding.CountersFragmentBinding
import com.vad.androidbasic.model.Counter
import com.vad.androidbasic.model.DataInterface
import com.vad.androidbasic.viewmodel.CountersViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalTime
@AndroidEntryPoint
class ListCounterFragment : Fragment() {
    private lateinit var binding: CountersFragmentBinding
    
    @Inject
    lateinit var dataInterface: DataInterface
    
    private val viewModel: CountersViewModel by viewModels()
    
    private val adapter by lazy {
        CounterAdapter(onItemClick)
    }
    
    private val onItemClick: (counter: Counter) -> Unit = { counter ->
        navigateCounter(counter)
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
    ): View = CountersFragmentBinding.inflate(inflater, container, false).also {
        binding = it
    }.root
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        
        binding.recycler.adapter = adapter
        viewModel.items.observe(viewLifecycleOwner, Observer { counters ->
            Toast.makeText(requireContext(), "Update", Toast.LENGTH_LONG).show()
            
            counters?.let {
                adapter.updateList(it)
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.recycler)
        
        viewModel.navigation.observe(viewLifecycleOwner, Observer {
            navigateCounter()
        })
    }
    
    private fun navigateCounter(counter: Counter? = null) {
        val action =
            ListCounterFragmentDirections.actionListCounterFragmentToCounterFragment(counter)
        findNavController().navigate(action)
    }
}
