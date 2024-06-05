package com.example.mindofthevalley

import androidx.databinding.ObservableBoolean

interface BaseViewModel {

    fun getEmptyViewVisibility(): ObservableBoolean

    fun getLoadingViewVisibility(): ObservableBoolean

    fun getNoInternetViewVisibility(): ObservableBoolean

    fun onLoading()

    fun onContent()

    fun onEmpty()

    fun onNoInternetFound()
}