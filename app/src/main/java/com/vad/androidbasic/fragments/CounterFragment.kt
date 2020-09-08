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
import com.vad.androidbasic.model.DataImplement
import com.vad.androidbasic.viewmodel.CounterViewModel
import com.vad.androidbasic.viewmodel.createViewModel
import kotlinx.android.synthetic.main.counter_fragment.plus1
import kotlinx.android.synthetic.main.counter_fragment.plus2
import kotlinx.android.synthetic.main.counter_fragment.textView

class CounterFragment: Fragment() {
    private val viewModel by lazy {
        createViewModel(this) {
            CounterViewModel(DataImplement.instance)
        }
    }

    private val onDataUpdate: (value: Int) -> Unit = {
        showData(it)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.counter_fragment, container, false).also {
        arguments?.getString(ID_KEY)?.let { id ->
            viewModel.updateCurrentId(id)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onDataUpdate(onDataUpdate)

        setHasOptionsMenu(true)
        plus1.setOnClickListener {
            viewModel.plusOne(onDataUpdate)
        }

        plus2.setOnClickListener {
            viewModel.plusTwo(onDataUpdate)
        }
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

    private fun showData(value: Int) {
        textView.text = "$value"
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