package me.kyd3snik.demo.lce

import android.view.View
import androidx.annotation.ContentView

interface ViewState<out T : Any?> {

    val isLoading get() = this is LoadingViewState
    val isContent get() = this is ContentViewState
    val dataOrNull get() = (this as? ContentViewState)?.data
    val errorOrNull get() = (this as? ErrorViewState)?.error
}

object LoadingViewState : ViewState<Nothing>
data class ContentViewState<T : Any?>(val data: T) : ViewState<T>
data class ErrorViewState(val error: Throwable, val retry: (() -> Unit)? = null) : ViewState<Nothing>

object ContentLoadingViewState : ViewState<Nothing>

data class CompositeViewState(
    val viewStates: List<ViewState<Any>>,
) : ViewState<Any> {
    constructor(vararg viewStates: ViewState<Any>) : this(viewStates.toList())
}
// etc


fun <T> ViewState<T>.onContent(action: (T) -> Unit) = apply {
    if (this is ContentViewState<T>) action(data)
}

fun <T> ViewState<T>.fallback(action: (ViewState<T>) -> Unit) {
    if (this !is ContentViewState<T>) action(this)
}