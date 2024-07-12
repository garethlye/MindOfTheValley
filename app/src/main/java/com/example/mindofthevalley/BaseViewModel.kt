package com.example.mindofthevalley

import android.view.View
import androidx.databinding.ObservableBoolean

interface BaseViewModel {

    fun getEmptyViewVisibility(): ObservableBoolean

    fun getLoadingViewVisibility(): ObservableBoolean

    fun getNoInternetViewVisibility(): ObservableBoolean

    fun onLoading()

    fun onContent()

    fun onEmpty()

    fun onNoInternetFound()

    fun onRetryClicked(view: View)
}