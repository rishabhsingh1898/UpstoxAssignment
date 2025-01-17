package com.rishabhsingh.upstoxassignment.models

import com.google.gson.annotations.SerializedName

data class StocksResponse(
    @SerializedName("data")
    val stocksData: StocksData
)
