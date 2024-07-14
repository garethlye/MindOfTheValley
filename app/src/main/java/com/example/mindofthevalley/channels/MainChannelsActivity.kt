package com.example.mindofthevalley.channels

import android.content.res.Configuration
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mindofthevalley.BaseActivity
import com.example.mindofthevalley.R
import com.example.mindofthevalley.adapters.CategoryAdapter
import com.example.mindofthevalley.adapters.ChannelsAdapter
import com.example.mindofthevalley.adapters.EpisodesAdapter
import com.example.mindofthevalley.databinding.ActivityMainChannelsBinding
import com.example.mindofthevalley.util.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainChannelsActivity : BaseActivity() {

    lateinit var binding: ActivityMainChannelsBinding
    private var gridLayoutManager = GridLayoutManager(this, 2)
    private val viewModel: MainChannelsViewModel by viewModels()
    override val layoutResource = R.layout.activity_main_channels

    private val channelsAdapter = ChannelsAdapter()
    private val categoryAdapter = CategoryAdapter()
    private val episodesAdapter = EpisodesAdapter()

    override fun bindViewModel() {
        binding = initBinding(viewModel)
        binding.lifecycleOwner = this
    }

    override fun setupView() {
        binding.recyclerView.adapter = channelsAdapter
        binding.categoriesRv.adapter = categoryAdapter
        binding.newEpisodesView.channelsRv.adapter = episodesAdapter
        binding.categoriesRv.layoutManager = gridLayoutManager
        binding.refreshLayout.setOnRefreshListener {
            //only called when it IS refreshing, clear the list.
            viewModel.onSwipeRefresh()
        }

        viewModel.showSwipeToRefreshLoading.observe(this) { loading ->
            binding.refreshLayout.isRefreshing = loading
        }

        viewModel.data.observe(this) { data ->
            if (data != null) {
                channelsAdapter.submitList(emptyList())
                categoryAdapter.submitList(emptyList())
                episodesAdapter.submitList(emptyList())
                data.channels?.let { channelsAdapter.submitList(it) }
                data.categories?.let { categoryAdapter.submitList(it) }
                data.newEpisodes?.let { episodesAdapter.submitList(it) }
                lifecycleScope.launch {
                    delay(2000)// give 2 second for all adapters to populate, not necessary but better UX
                    viewModel.onContent()
                }
            }
        }

        viewModel.backupData.observe(this) { backupData ->
            try {
                if (backupData != null) {
                    channelsAdapter.submitList(emptyList())
                    categoryAdapter.submitList(emptyList())
                    episodesAdapter.submitList(emptyList())
                    backupData.channels?.let { channelsAdapter.submitList(it) }
                    backupData.categories?.let { categoryAdapter.submitList(it) }
                    backupData.newEpisodes?.let { episodesAdapter.submitList(it) }
                    viewModel.onContent()
                    return@observe
                }
                //if no backups detected, show no internet screen
                viewModel.onNoInternetFound()
            } catch (e: Exception) {
                //IF any other exceptions were detected trying to load backups,
                //assume app failed in some way, show error screen
                viewModel.onEmpty()
            }
        }

        if(NetworkUtils.isNetworkConnected(this)) {
            viewModel.onLoading()
            viewModel.fetchData()
            return
        }
        viewModel.tryToLoadBackup()
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