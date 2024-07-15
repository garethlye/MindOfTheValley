package com.example.mindofthevalley

import android.app.Application
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mindofthevalley.channels.MainChannelsViewModel
import com.example.mindofthevalley.data.Data
import com.example.mindofthevalley.interactor.ChannelsInteractor
import com.example.mindofthevalley.manager.AppDataManager
import com.example.mindofthevalley.network.ChannelsAPI
import com.example.mindofthevalley.network.ChannelsApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlin.test.assertNotNull

@ExperimentalCoroutinesApi
class MainChannelsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Mock
    private lateinit var dataObserver: Observer<Data?>

    @Mock
    private lateinit var backupDataObserver: Observer<Data?>

    @Mock
    private lateinit var loadingObserver: Observer<Boolean>

    @Mock
    private lateinit var mockApplication: Application

    @Mock
    private lateinit var mockAppDataManager: AppDataManager

    @Mock
    private lateinit var mockChannelsAPI: ChannelsAPI

    @Mock
    lateinit var mockEmptyView: View

    private lateinit var channelsInteractor: ChannelsInteractor
    private lateinit var viewModel: MainChannelsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        channelsInteractor = ChannelsInteractor(mockAppDataManager, mockChannelsAPI)
        viewModel = MainChannelsViewModel(mockApplication, channelsInteractor)
        viewModel.data.observeForever(dataObserver)
        viewModel.backupData.observeForever(backupDataObserver)
        viewModel.showSwipeToRefreshLoading.observeForever(loadingObserver)
    }

    @Test
    fun `fetchData should update data object`() = testScope.runTest {
        val mockData = Data(channels = ArrayList(), categories = ArrayList(), newEpisodes = ArrayList())
        val mockChannelsApiResponse = ChannelsApiResponse(mockData)
        `when`(channelsInteractor.getEpisodes()).thenReturn(mockChannelsApiResponse)
        `when`(channelsInteractor.getChannels()).thenReturn(mockChannelsApiResponse)
        `when`(channelsInteractor.getCategories()).thenReturn(mockChannelsApiResponse)

        //Testing FetchData()
        assertNotNull(mockData) //data object shouldn't be null, but the information inside it would be
        viewModel.fetchData()
        advanceTimeBy(2000) //wait a bit for fetchData to execute the update
        verify(dataObserver).onChanged(mockData) //fetchData updates the "Data" LiveData object
        verify(mockAppDataManager).updateLastConnectedData(mockData) //fetchData also stores the data object to local for offline-mode
    }

    @Test
    fun `tryToLoadBackup function test`() = testScope.runTest {
        val mockData = Data(channels = ArrayList(), categories = ArrayList(), newEpisodes = ArrayList())
        `when`(mockAppDataManager.getLastConnectedData()).thenReturn(mockData)

        //Testing tryToLoadBackup()
        viewModel.tryToLoadBackup()
        advanceTimeBy(2000) //wait a bit for fetchData to execute the update

        verify(channelsInteractor.appDataManager).getLastConnectedData() //check that this is called
        assertNotNull(viewModel.backupData.value) //check that this is update and no longer null
    }

    @Test
    fun `test onSwipeRefresh calls LiveData loading flag`() = testScope.runTest {
        viewModel.onSwipeRefresh()
        advanceTimeBy(500)

        verify(loadingObserver, atLeastOnce()).onChanged(true) //if loader has changed, fetchData is called
    }

    @Test
    fun `test onRetryClicked calls LiveData loading flag`() = testScope.runTest {
        viewModel.onRetryClicked(mockEmptyView)
        advanceTimeBy(500)

        verify(loadingObserver, atLeastOnce()).onChanged(false) //if loader has changed, fetchData is called
    }

    @After
    fun tearDown() {
        //Cleanup
        viewModel.data.removeObserver(dataObserver)
        viewModel.backupData.removeObserver(backupDataObserver)
        viewModel.showSwipeToRefreshLoading.removeObserver(loadingObserver)
        Dispatchers.resetMain()
    }
}