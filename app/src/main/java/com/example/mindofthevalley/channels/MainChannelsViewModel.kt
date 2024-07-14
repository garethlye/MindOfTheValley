package com.example.mindofthevalley.channels

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindofthevalley.BaseViewModelImpl
import com.example.mindofthevalley.data.Data
import com.example.mindofthevalley.interactor.ChannelsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainChannelsViewModel @Inject constructor(
    application: Application,
    private val channelsInteractor: ChannelsInteractor,
): BaseViewModelImpl(application) {

    val showSwipeToRefreshLoading = MutableLiveData(false)
    val data = MutableLiveData<Data?>() //Data will be the parent "Model"
    val backupData = MutableLiveData<Data?>()

    fun fetchData() {
        showSwipeToRefreshLoading.value = false
        onLoading()
        viewModelScope.launch {
            try {
                channelsInteractor.getEpisodes()
                val dataFromApi = listOf( //call in async so no blocking compared to sequential API calls
                    async { runCatching { channelsInteractor.getEpisodes() }.getOrNull()},
                    async { runCatching { channelsInteractor.getChannels() }.getOrNull()},
                    async { runCatching { channelsInteractor.getCategories() }.getOrNull()}
                )

                val apiResults = dataFromApi.awaitAll()
                val latestData = Data(apiResults[1]?.data?.channels, apiResults[2]?.data?.categories, apiResults[0]?.data?.newEpisodes)
                data.value = latestData
                //Migrate to single model so we can populate the entire view
                //with one class and store that entire model class for offline-mode
                channelsInteractor.appDataManager.updateLastConnectedData(latestData)
            } catch (e: Exception) {
                //assume one or more API failed or an exception
                //typically you can check if the network failed at this exception check but since we are loading backups, we will ignore it first
                tryToLoadBackup()
            }
        }
    }

    fun tryToLoadBackup() {
        val lastBackupData = channelsInteractor.appDataManager.getLastConnectedData()
        backupData.value = lastBackupData
    }

    fun onSwipeRefresh() {
        showSwipeToRefreshLoading.value = true
        fetchData()
    }

    override fun onRetryClicked(view: View) {
        super.onRetryClicked(view)
        fetchData()
    }

}