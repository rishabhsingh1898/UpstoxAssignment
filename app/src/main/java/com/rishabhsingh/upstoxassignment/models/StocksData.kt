package com.rishabhsingh.upstoxassignment.models

import com.google.gson.annotations.SerializedName

data class StocksData(
    @SerializedName("userHolding")
    val stocksList : List<Stocks>
)
