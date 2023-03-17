package com.example.nycschoolsapp.data.api

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

const val CONNECT_TIMEOUT_SECONDS = 10L
const val READ_TIMEOUT_SECONDS = 10L

class HttpClient {
    var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    private object Instance {
        val instanceClient: OkHttpClient = HttpClient().okHttpClient
    }

    companion object {
        val client: OkHttpClient by lazy { Instance.instanceClient }
    }
}





