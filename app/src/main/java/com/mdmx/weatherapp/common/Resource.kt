package com.mdmx.weatherapp.common

sealed class Resource<T>(val data: T? = null, val massage: String? = null) {
    class Success<T>(  data: T) : Resource<T>(data)
    class Failure<T>(massage: String, data: T? = null) : Resource<T>(data, massage)
    class Loading<T> : Resource<T>()
}