package com.example.mindofthevalley.util

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class CustomLiveEvent<T> : MutableLiveData<T>() {
    private val pendingEvent = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        // Observe the internal MutableLiveData
        super.observe(owner) { t ->
            if (pendingEvent.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        pendingEvent.set(true)
        super.setValue(t)
    }

    @MainThread
    fun call() {
        value = null
    }
}