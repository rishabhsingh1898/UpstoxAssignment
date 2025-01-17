package com.rishabhsingh.upstoxassignment.di

import com.rishabhsingh.upstoxassignment.api.NetworkConstants.BASE_URL
import com.rishabhsingh.upstoxassignment.api.StockApi
import com.rishabhsingh.upstoxassignment.repository.stocks.StocksRepository
import com.rishabhsingh.upstoxassignment.repository.stocks.StocksRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun provideStockApi(retrofit: Retrofit): StockApi {
        return retrofit.create(StockApi::class.java)
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideStocksRepository(api: StockApi): StocksRepository {
        return StocksRepositoryImpl(api)
    }
}