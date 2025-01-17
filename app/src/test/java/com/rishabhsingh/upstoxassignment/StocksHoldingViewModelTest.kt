package com.rishabhsingh.upstoxassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rishabhsingh.upstoxassignment.api.NetworkState
import com.rishabhsingh.upstoxassignment.models.Stocks
import com.rishabhsingh.upstoxassignment.repository.stocks.StocksRepository
import com.rishabhsingh.upstoxassignment.util.calculateProfitPercentage
import com.rishabhsingh.upstoxassignment.viewmodel.stock.StocksHoldingViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever


@OptIn(ExperimentalCoroutinesApi::class)
class StocksHoldingViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var stocksRepository: StocksRepository
    private lateinit var viewModel: StocksHoldingViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        stocksRepository = mock()
        viewModel = StocksHoldingViewModel(stocksRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getStocks emits Success state with non-empty stocks list`() = runTest {
        val mockStocks = listOf(
            Stocks("AAPL", 10, 150.0f, 120.0f, 155.0f),
            Stocks("GOOGL", 5, 2800.0f, 2500.0f, 2900.0f)
        )
        whenever(stocksRepository.getStocks()).thenReturn(mockStocks)

        viewModel.getStocks()
        testDispatcher.scheduler.advanceUntilIdle()

        val stocksState = viewModel.stocksState.first()
        assertTrue(stocksState is NetworkState.Success)
        assertEquals(mockStocks, (stocksState as NetworkState.Success).data)
    }

    @Test
    fun `getStocks emits Empty state when stocks list is empty`() = runTest {
        whenever(stocksRepository.getStocks()).thenReturn(emptyList())

        viewModel.getStocks()
        testDispatcher.scheduler.advanceUntilIdle()

        val stocksState = viewModel.stocksState.first()
        assertTrue(stocksState is NetworkState.Empty)
    }

    @Test
    fun `getStocks emits Failure state when an exception occurs`() = runTest {
        val exceptionMessage = "Failed to fetch stocks"
        whenever(stocksRepository.getStocks()).thenThrow(RuntimeException(exceptionMessage))

        viewModel.getStocks()
        testDispatcher.scheduler.advanceUntilIdle()

        val stocksState = viewModel.stocksState.first()
        assertTrue(stocksState is NetworkState.Failure)
        assertEquals(exceptionMessage, (stocksState as NetworkState.Failure).errorMessage)
    }

    @Test
    fun `calculateAggregateValues updates aggregateState correctly`() = runTest {
        val mockStocks = listOf(
            Stocks("AAPL", 10, 150.0f, 120.0f, 155.0f),
            Stocks("GOOGL", 5, 2800.0f, 2500.0f, 2900.0f)
        )

        viewModel.calculateAggregateValues(mockStocks)

        val aggregateState = viewModel.aggregateState.first()
        assertEquals(1500.0f + 14000.0f, aggregateState.totalInvestedValue)
        assertEquals(1200.0f + 12500.0f, aggregateState.totalCurrentInvestedValue)
        assertEquals((155.0f - 150.0f) * 10 + (2900.0f - 2800.0f) * 5, aggregateState.sumOfTodayProfitAndLoss, 0.01f)
        assertEquals(
            calculateProfitPercentage(1500.0f + 14000.0f, 1200.0f + 12500.0f),
            aggregateState.profitAndLossPercentage
        )
    }
}
