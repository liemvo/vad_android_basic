package com.vad.androidbasic.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vad.androidbasic.R
import com.vad.androidbasic.databinding.CounterFragmentBinding
import com.vad.androidbasic.model.DataInterface
import com.vad.androidbasic.viewmodel.CounterViewModel
import com.vad.androidbasic.viewmodel.createViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CounterFragment : Fragment() {
    private lateinit var binding: CounterFragmentBinding
    
    @Inject
    lateinit var dataInterface: DataInterface
    
    private val viewModel: CounterViewModel by viewModels()
    
    private val args: CounterFragmentArgs by navArgs()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = CounterFragmentBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.updateCurrentCounter(args.counter)
        
        setHasOptionsMenu(true)
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.counter_menu, menu)
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionSave) {
            Toast.makeText(requireContext(), "Save action", Toast.LENGTH_LONG).show()
            viewModel.saveOrUpdate {
                findNavController().navigateUp()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}