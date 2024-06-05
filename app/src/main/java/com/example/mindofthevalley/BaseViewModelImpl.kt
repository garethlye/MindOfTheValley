package com.example.mindofthevalley

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import com.example.mindofthevalley.core.RetrofitClientSingleton
import retrofit2.Retrofit

open class BaseViewModelImpl (application: Application): AndroidViewModel(application), BaseViewModel {
    private val emptyViewVisibility = ObservableBoolean(false)
    private val loadingViewVisibility = ObservableBoolean(false)
    private val noInternetViewVisibility = ObservableBoolean(false)

    fun getApiNetwork(): Retrofit {
        return RetrofitClientSingleton.instance
    }

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
    }

    override fun onContent() {
        loadingViewVisibility.set(false)
        emptyViewVisibility.set(false)
    }

    override fun onEmpty() {
        loadingViewVisibility.set(false)
        emptyViewVisibility.set(true)
    }

    override fun onNoInternetFound() {
        noInternetViewVisibility.set(true)
        emptyViewVisibility.set(false)
        loadingViewVisibility.set(false)
    }
}