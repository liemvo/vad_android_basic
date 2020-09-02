package com.vad.androidbasic.model

class DataImplement: DataInterface {
    private val _items = mutableListOf<Counter>()

    private object Holder {
        val dataController = DataImplement()
    }

    var neeUpdate: (willUpdate: Boolean) -> Unit = {}

    override val items: List<Counter>
        get() = _items

    override fun removeItem(counter: Counter) {
        _items.removeAll { it.id == counter.id }
    }

    override fun addOrUpdateItem(counter: Counter) {
        val index = _items.indexOf(counter)
        if (index in 0.._items.size) {
            _items[index] = counter
        } else {
            _items.add(counter)
        }
        neeUpdate(true)
    }

    companion object {
        val instance = Holder.dataController
    }
}