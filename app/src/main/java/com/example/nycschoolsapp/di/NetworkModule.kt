package com.example.nycschoolsapp.di

import android.net.Uri
import com.example.nycschoolsapp.data.api.HttpClient
import com.example.nycschoolsapp.data.api.UriTypeAdapter
import com.example.nycschoolsapp.data.api.ApiConstants
import com.example.nycschoolsapp.data.api.NycSchoolsApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideNetworkService(): NycSchoolsApiService {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.SCHOOLS_INFO_BASE_URL)
            .client(HttpClient.client)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .registerTypeAdapter(Uri::class.java, UriTypeAdapter())
                        .create()
                )
            )
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(NycSchoolsApiService::class.java)
    }
}