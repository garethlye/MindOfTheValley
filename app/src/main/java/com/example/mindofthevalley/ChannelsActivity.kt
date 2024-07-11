package com.example.mindofthevalley

import android.content.res.Configuration
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mindofthevalley.databinding.ActivityChannelsBinding
import com.example.mindofthevalley.util.NetworkUtils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChannelsActivity : BaseActivity() {
    lateinit var binding: ActivityChannelsBinding
    private var gridLayoutManager = GridLayoutManager(this, 2)
    private val viewModel: ChannelsViewModelImpl by viewModels()
    override val layoutResource = R.layout.activity_channels

    override fun bindViewModel() {
        binding = initBinding(viewModel)

        viewModel.saveLocalStorage.observe(this) {
            saveJsonToSharedPreferences(it, "DATA", "DATA_KEY")
        }
        viewModel.checkLocalStorage.observe(this) {
            try {
                val data = getJsonFromSharedPreferences("DATA", "DATA_KEY")
                if(data != null) {
                    viewModel.populateViews(data, DataType.All)
                }
            } catch (e: Exception) {
                //if storage is empty, just show empty state
                viewModel.onEmpty()
            }
        }
    }

    override fun setupView() {
        if(NetworkUtils.isNetworkConnected(this)) {
            binding.recyclerView.adapter = viewModel.channelsAdapter
            binding.categoriesRv.adapter = viewModel.categoryAdapter
            binding.newEpisodesView.channelsRv.adapter = viewModel.episodesAdapter
            binding.categoriesRv.layoutManager = gridLayoutManager
            viewModel.setupView()
            return
        }
        viewModel.onNoInternetFound()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        gridLayoutManager.spanCount = if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            4 // could also calculate based on horizontal pixel density but it is more taxxing on the CPU, quick and dirty
        } else {
            2 // 2 columns to follow portrait figma design
        }
    }
}