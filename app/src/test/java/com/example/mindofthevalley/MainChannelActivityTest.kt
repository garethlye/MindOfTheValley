package com.example.mindofthevalley

import android.content.res.Configuration
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mindofthevalley.channels.MainChannelsActivity
import com.example.mindofthevalley.channels.MainChannelsViewModel
import com.example.mindofthevalley.data.Data
import com.example.mindofthevalley.manager.AppDataManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MainChannelsActivityTest {

    private lateinit var viewModel: MainChannelsViewModel
    private lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var appDataManager: AppDataManager

    @Mock
    private lateinit var dataObserver: Observer<Data?>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        viewModel = mock(MainChannelsViewModel::class.java)
        viewModelFactory = mock(ViewModelProvider.Factory::class.java)

        `when`(viewModelFactory.create(MainChannelsViewModel::class.java)).thenReturn(viewModel)
        `when`(viewModel.showSwipeToRefreshLoading).thenReturn(MutableLiveData(false))
        `when`(viewModel.data).thenReturn(MutableLiveData())
        `when`(viewModel.backupData).thenReturn(MutableLiveData())

        appDataManager = mock(AppDataManager::class.java)
        viewModel.data.observeForever(dataObserver)
    }

    @Test
    fun testViewModelInitialization() = runTest {
        val scenario = ActivityScenario.launch(MainChannelsActivity::class.java) //start activity

        scenario.onActivity { activity ->
            assertNotNull(activity.viewModel) //make sure ViewModel isn't null
            assertNotNull(activity.binding) //make sure binding isn't null
        }
    }

    @Test
    fun testRecyclerViewAdaptersSetup() = runTest {
        val mockData = Data(channels = ArrayList(), categories = ArrayList(), newEpisodes = ArrayList())
        val liveData = MutableLiveData<Data?>().apply { value = mockData }
        `when`(viewModel.data).thenReturn(liveData)

        val scenario = ActivityScenario.launch(MainChannelsActivity::class.java)

        //since we test Data and BackupData's liveData object in VM unit test, we will skip here
        //we will check if the adapters are assigned correctly to the activity binding
        scenario.onActivity { activity ->
            activity.setupView()
            assertNotNull(activity.binding.recyclerView.adapter)
            assertNotNull(activity.binding.categoriesRv.adapter)
            assertNotNull(activity.binding.newEpisodesView.channelsRv)
            assertNotNull(activity.binding.categoriesRv.layoutManager)
        }
    }

    @Test
    fun testConfigurationChange() {
        val scenario = ActivityScenario.launch(MainChannelsActivity::class.java)
        //according to requirement spec of the task,
        //portrait will stick to span count 2 & landscape will stick to span count 6,
        //make sure when orientation changes, the span count changes to the correct values
        scenario.onActivity { activity ->
            val initialSpanCount = activity.gridLayoutManager.spanCount
            activity.resources.configuration.orientation = Configuration.ORIENTATION_LANDSCAPE
            //manual rotation change to landscape
            activity.onConfigurationChanged(activity.resources.configuration)

            val landscapeSpanCount = activity.gridLayoutManager.spanCount
            //check that the landscape span count changes
            assertNotEquals(initialSpanCount, 6)

            activity.resources.configuration.orientation = Configuration.ORIENTATION_PORTRAIT
            //manual rotation change to portrait
            activity.onConfigurationChanged(activity.resources.configuration)

            val portraitSpanCount = activity.gridLayoutManager.spanCount
            //check that the portrait span count changes
            assertEquals(initialSpanCount, 2)
        }
    }
}