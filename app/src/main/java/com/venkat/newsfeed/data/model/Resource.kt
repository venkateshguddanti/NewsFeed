package com.venkat.newsfeed.data.model

import com.venkat.newsfeed.utils.Status

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = null)
        fun <T> error(data: T?, message: String?): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)
        fun <T> loading(data: T?): Resource<T> = Resource(Status.LOADING, data, null)
    }
}