package me.kyd3snik.demo.lce

sealed class Request<out T : Any?> {
    object Loading : Request<Nothing>()
    data class Content<T : Any?>(val data: T) : Request<T>()
    data class Error(val error: Throwable) : Request<Nothing>()

    val isLoading get() = this is Loading
    val isContent get() = this is Content
    val dataOrNull get() = (this as? Content)?.data
    val errorOrNull get() = (this as? Error)?.error
}