package com.example.nycschoolsapp.data.api

sealed class Resource<T>(val data: T, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T) : Resource<T>(data, message)
}