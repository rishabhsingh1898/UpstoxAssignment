package com.rishabhsingh.upstoxassignment

import com.rishabhsingh.upstoxassignment.api.StockApi
import com.rishabhsingh.upstoxassignment.models.Stocks
import com.rishabhsingh.upstoxassignment.models.StocksData
import com.rishabhsingh.upstoxassignment.models.StocksResponse
import com.rishabhsingh.upstoxassignment.repository.stocks.StocksRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class StocksRepositoryImplTest {

    @Mock
    private lateinit var mockApi:StockApi

    private var stocksRepository: StocksRepositoryImpl

    init {
        MockitoAnnotations.openMocks(this)
        stocksRepository = StocksRepositoryImpl(mockApi)
    }



    @Test
    fun `test getStocks success`() = runBlocking {
        // Prepare mock data
        val mockStocks = listOf(
            Stocks("ABC", 10, 100.0f, 90.0f, 95.0f),
            Stocks("XYZ", 5, 200.0f, 180.0f, 190.0f)
        )
        val mockApiResponse = StocksResponse(StocksData(mockStocks))
        whenever(mockApi.getStocks()).thenReturn(mockApiResponse)

        val result = stocksRepository.getStocks()

        Assert.assertNotNull(result)
        Assert.assertEquals(2, result.size)
        Assert.assertEquals("ABC", result[0].symbol)
        Assert.assertEquals(10, result[0].quantity)
        Assert.assertEquals(100.0f, result[0].ltp, 0.0f)
    }

    @Test(expected = Exception::class)
    fun `test getStocks failure`() = runBlocking {
        whenever(mockApi.getStocks()).thenThrow(Exception("API error"))


        stocksRepository.getStocks()
        Assert.fail("Expected exception to be thrown")
    }
}