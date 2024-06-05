package com.example.mindofthevalley

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.viewModelScope
import com.example.mindofthevalley.adapters.CategoryAdapter
import com.example.mindofthevalley.adapters.ChannelsAdapter
import com.example.mindofthevalley.adapters.EpisodesAdapter
import com.example.mindofthevalley.data.Data
import com.example.mindofthevalley.network.ChannelsAPI
import com.example.mindofthevalley.util.CustomLiveEvent
import kotlinx.coroutines.launch

class ChannelsViewModelImpl constructor(application: Application): BaseViewModelImpl(application), ChannelsAdapter.Interaction {

    val channelsAdapter = ChannelsAdapter(this)
    val categoryAdapter = CategoryAdapter()
    val episodesAdapter = EpisodesAdapter()
    val showSwipeToRefreshLoading = ObservableBoolean()

    val saveLocalStorage = CustomLiveEvent<Data>()
    val checkLocalStorage = CustomLiveEvent<Any>()

    fun setupView() {
        viewModelScope.launch {
            try {
                onLoading()
                showSwipeToRefreshLoading.set(false)
                val apiBase = getApiNetwork().create(ChannelsAPI::class.java)
                val episodesResponse = apiBase.getEpisodes()
                val channelsResponse = apiBase.getChannels()
                val categoryResponse = apiBase.getCategories()
                var savedData = Data(null, null, null)
                if(channelsResponse.data?.channels != null) {
                    populateViews(channelsResponse.data, BaseActivity.DataType.Channels)
                    savedData.channels = channelsResponse.data.channels
                }
                if(categoryResponse.data?.categories != null) {
                    populateViews(categoryResponse.data, BaseActivity.DataType.Category)
                    savedData.categories = categoryResponse.data.categories
                }
                if(episodesResponse.data?.newEpisodes != null) {
                    populateViews(episodesResponse.data, BaseActivity.DataType.Episodes)
                    savedData.newEpisodes = episodesResponse.data.newEpisodes
                }
                saveLocalStorage.postValue(savedData)

                Handler(Looper.getMainLooper()).postDelayed({
                    onContent() // give 1 second for all adapters to populate, better UX
                }, 2000)

            } catch (e: Exception) {
                //assume one or more API failed
                //check for local storage if data is available
                checkLocalStorage.postValue(Any())
            }
        }
    }

    fun populateViews(data: Data, type: BaseActivity.DataType) {
        when(type) {
            BaseActivity.DataType.Channels -> {
                channelsAdapter.submitList(data.channels!!)
            }
            BaseActivity.DataType.Category -> {
                categoryAdapter.submitList(data.categories!!)
            }
            BaseActivity.DataType.Episodes -> {
                episodesAdapter.submitList(data.newEpisodes!!)
            }
            BaseActivity.DataType.All -> {
                channelsAdapter.submitList(data.channels!!)
                categoryAdapter.submitList(data.categories!!)
                episodesAdapter.submitList(data.newEpisodes!!)
            }
        }
    }

    fun onRefresh() {
        showSwipeToRefreshLoading.set(true)
        channelsAdapter.submitList(emptyList())
        categoryAdapter.submitList(emptyList())
        episodesAdapter.submitList(emptyList())
        setupView()
    }
}