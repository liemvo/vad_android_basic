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
import com.vad.androidbasic.model.Counter
import com.vad.androidbasic.model.DataImplement
import kotlinx.android.synthetic.main.counter_fragment.plus1
import kotlinx.android.synthetic.main.counter_fragment.plus2
import kotlinx.android.synthetic.main.counter_fragment.textView

class CounterFragment: Fragment() {
    private var value = 0
    private var currentCounter: Counter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.counter_fragment, container, false).also {
        arguments?.getString(ID_KEY)?.let { id ->
            currentCounter = DataImplement.instance.items.firstOrNull { it.id == id }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        value = currentCounter?.value ?: 0
        showData()
        setHasOptionsMenu(true)
        plus1.setOnClickListener {
            value += 1
            showData()
        }

        plus2.setOnClickListener {
            value += 2
            showData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.counter_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionSave) {
            Toast.makeText(requireContext(), "Save action", Toast.LENGTH_LONG).show()
            val counter = currentCounter?.copy(value = value) ?: Counter(value = value, dateInMillis = System.currentTimeMillis())
            DataImplement.instance.addOrUpdateItem(counter)
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showData() {
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