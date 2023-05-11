package me.kyd3snik.demo.lce.ui

import me.kyd3snik.demo.lce.Request

interface LCEView {
    fun showLoading()
    fun showContent()
    fun showError(error: Throwable, retry: () -> Unit = {})

    fun <T> showRequest(
        request: Request<T>,
        retry: () -> Unit = {},
        showContent: (T) -> Unit
    ) = when (request) {
        Request.Loading -> showLoading()
        is Request.Content -> {
            showContent()
            showContent(request.data)
        }
        is Request.Error -> showError(request.error, retry)
    }
}