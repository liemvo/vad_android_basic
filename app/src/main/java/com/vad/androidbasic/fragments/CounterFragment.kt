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
import com.vad.androidbasic.R
import com.vad.androidbasic.databinding.CounterFragmentBinding
import com.vad.androidbasic.model.DataImplement
import com.vad.androidbasic.viewmodel.CounterViewModel
import com.vad.androidbasic.viewmodel.createViewModel

class CounterFragment: Fragment() {
    private lateinit var binding: CounterFragmentBinding
    private val viewModel by lazy {
        createViewModel(this) {
            CounterViewModel(DataImplement.instance)
        }
    }

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
        arguments?.getString(ID_KEY)?.let { id ->
            viewModel.updateCurrentId(id)
        }

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
                if (it) activity?.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val ID_KEY = "id_key"
        fun newInstance(id: String? = null) = CounterFragment().apply {
            arguments = Bundle().apply {
                putString(ID_KEY, id)
            }
        }
    }
}