package com.example.mindofthevalley

import android.app.Application
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel

open class BaseViewModelImpl (application: Application): AndroidViewModel(application), BaseViewModel {
    private val emptyViewVisibility = ObservableBoolean(false)
    private val loadingViewVisibility = ObservableBoolean(false)
    private val noInternetViewVisibility = ObservableBoolean(false)

    override fun getEmptyViewVisibility(): ObservableBoolean {
        return emptyViewVisibility
    }

    override fun getLoadingViewVisibility(): ObservableBoolean {
        return loadingViewVisibility
    }

    override fun getNoInternetViewVisibility(): ObservableBoolean {
        return noInternetViewVisibility
    }

    override fun onLoading() {
        loadingViewVisibility.set(true)
        emptyViewVisibility.set(false)
        noInternetViewVisibility.set(false)
    }

    override fun onContent() {
        noInternetViewVisibility.set(false)
        loadingViewVisibility.set(false)
        emptyViewVisibility.set(false)
    }

    override fun onEmpty() {
        noInternetViewVisibility.set(false)
        loadingViewVisibility.set(false)
        emptyViewVisibility.set(true)
    }

    override fun onNoInternetFound() {
        noInternetViewVisibility.set(true)
        emptyViewVisibility.set(false)
        loadingViewVisibility.set(false)
    }

    override fun onRetryClicked(view: View) {

    }
}