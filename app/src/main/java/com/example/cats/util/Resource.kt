package com.example.cats.util

//a generic class, "data" in case of success, "message" if there's an error
sealed class Resource<T>(val data: T?, val message: String?) {
    class Success<T>(data: T): Resource<T>(data, null)
    class Error<T>(message: String): Resource<T>(null, message)
}