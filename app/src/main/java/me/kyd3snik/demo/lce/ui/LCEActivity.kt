package me.kyd3snik.demo.lce.ui

import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import me.kyd3snik.demo.R

open class LCEActivity : AppCompatActivity(), LCEView {

    private val contentView: View get() = findViewById(R.id.loading)
    private val loadingView: View get() = findViewById(R.id.loading)
    private val errorView: TextView get() = findViewById(R.id.error)

    override fun showLoading() {
        contentView.isVisible = false
        errorView.isVisible = false
        loadingView.isVisible = true
    }

    override fun showContent() {
        contentView.isVisible = true
        errorView.isVisible = false
        loadingView.isVisible = false
    }

    override fun showError(error: Throwable, retry: () -> Unit ) {
        loadingView.isVisible = false
        contentView.isVisible = false
        errorView.isVisible = true

        errorView.text = error.message
        errorView.setOnClickListener { retry() }
    }
}