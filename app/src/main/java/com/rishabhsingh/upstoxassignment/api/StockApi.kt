package com.rishabhsingh.upstoxassignment.api

import com.rishabhsingh.upstoxassignment.models.StocksResponse
import retrofit2.http.GET

interface StockApi {

    @GET("/")
    suspend fun getStocks(): StocksResponse
}