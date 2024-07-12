package com.example.mindofthevalley

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.viewModelScope
import com.example.mindofthevalley.adapters.CategoryAdapter
import com.example.mindofthevalley.adapters.ChannelsAdapter
import com.example.mindofthevalley.adapters.EpisodesAdapter
import com.example.mindofthevalley.data.Data
import com.example.mindofthevalley.data.DataType
import com.example.mindofthevalley.interactor.ChannelsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChannelsViewModelImpl @Inject constructor(
    application: Application,
    private val channelsInteractor: ChannelsInteractor,
    ): BaseViewModelImpl(application), ChannelsAdapter.Interaction {

    val channelsAdapter = ChannelsAdapter(this)
    val categoryAdapter = CategoryAdapter()
    val episodesAdapter = EpisodesAdapter()
    val showSwipeToRefreshLoading = ObservableBoolean()

    fun setupView() {
        viewModelScope.launch {
            try {
                onLoading()
                showSwipeToRefreshLoading.set(false)
                val episodesResponse = channelsInteractor.getEpisodes()
                val channelsResponse = channelsInteractor.getChannels()
                val categoryResponse = channelsInteractor.getCategories()
                val savedData = Data(null, null, null)
                if(channelsResponse.data?.channels != null) {
                    populateViews(channelsResponse.data, DataType.Channels)
                    savedData.channels = channelsResponse.data.channels
                }
                if(categoryResponse.data?.categories != null) {
                    populateViews(categoryResponse.data, DataType.Category)
                    savedData.categories = categoryResponse.data.categories
                }
                if(episodesResponse.data?.newEpisodes != null) {
                    populateViews(episodesResponse.data, DataType.Episodes)
                    savedData.newEpisodes = episodesResponse.data.newEpisodes
                }
                channelsInteractor.appDataManager.updateLastConnectedData(savedData)
                Handler(Looper.getMainLooper()).postDelayed({
                    onContent() // give 2 second for all adapters to populate, better UX
                }, 2000)

            } catch (e: Exception) {
                //assume one or more API failed or an exception
                tryToLoadBackup()
            }
        }
    }

    fun tryToLoadBackup() {
        val backupData = channelsInteractor.appDataManager.getLastConnectedData()
        if(backupData != null) {
            //backup data isn't null, populate views with previous data
            populateViews(backupData, DataType.All)
            onContent()
            return
        }
        //if no backups detected, show no internet screen
        onNoInternetFound()
    }

    fun populateViews(data: Data, type: DataType) {
        when(type) {
            DataType.Channels -> {
                data.channels?.let { channelsAdapter.submitList(it) }
            }
            DataType.Category -> {
                data.categories?.let { categoryAdapter.submitList(it) }
            }
            DataType.Episodes -> {
                data.newEpisodes?.let { episodesAdapter.submitList(it) }
            }
            DataType.All -> {
                data.channels?.let { channelsAdapter.submitList(it) }
                data.categories?.let { categoryAdapter.submitList(it) }
                data.newEpisodes?.let { episodesAdapter.submitList(it) }
            }
        }
    }

    fun onSwipeRefresh() {
        showSwipeToRefreshLoading.set(true)
        onRefresh()
    }

    fun onRefresh() {
        channelsAdapter.submitList(emptyList())
        categoryAdapter.submitList(emptyList())
        episodesAdapter.submitList(emptyList())
        setupView()
    }

    override fun onRetryClicked(view: View) {
        super.onRetryClicked(view)
        onRefresh()
    }
}