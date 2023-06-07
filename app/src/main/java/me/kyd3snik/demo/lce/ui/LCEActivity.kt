package me.kyd3snik.demo.lce.ui

import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import me.kyd3snik.demo.R
import me.kyd3snik.demo.lce.ViewState
import kotlin.reflect.KClass

open class LCEActivity : AppCompatActivity(), LCEView {

    private val contentView: View get() = findViewById(R.id.loading)
    private val loadingView: View get() = findViewById(R.id.loading)
    private val errorView: TextView get() = findViewById(R.id.error)

     fun showLoading() {
        contentView.isVisible = false
        errorView.isVisible = false
        loadingView.isVisible = true
    }

     fun showContent() {
        contentView.isVisible = true
        errorView.isVisible = false
        loadingView.isVisible = false
    }

    fun showError(error: Throwable, retry: () -> Unit ) {
        loadingView.isVisible = false
        contentView.isVisible = false
        errorView.isVisible = true

        errorView.text = error.message
        errorView.setOnClickListener { retry() }
    }

    override fun <T : ViewState<*>> registerRenderer(viewState: KClass<T>, renderer: ViewStateRenderer<T>) {
        TODO("Not yet implemented")
    }

    override fun <T : ViewState<*>> unregisterRenderer(viewState: KClass<T>) {
        TODO("Not yet implemented")
    }

    override fun <T : Any> showViewState(viewState: ViewState<T>, showContent: (T) -> Unit) {
        TODO("Not yet implemented")
    }
}