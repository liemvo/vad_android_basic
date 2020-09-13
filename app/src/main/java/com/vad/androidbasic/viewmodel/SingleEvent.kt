package com.vad.androidbasic.viewmodel

import androidx.lifecycle.Observer

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class SingleLiveEvent<out T>(private val content: T) {

    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

/**
 * An [Observer] for [SingleLiveEvent]s, simplifying the pattern of checking if the [SingleLiveEvent]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [SingleLiveEvent]'s contents has not been handled.
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<SingleLiveEvent<T>> {
    override fun onChanged(singleLiveEvent: SingleLiveEvent<T>?) {
        singleLiveEvent?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}