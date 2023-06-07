package me.kyd3snik.demo.lce.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import me.kyd3snik.demo.databinding.ViewStateErrorBinding
import me.kyd3snik.demo.databinding.ViewStateLoadingBinding
import me.kyd3snik.demo.lce.CompositeViewState
import me.kyd3snik.demo.lce.ContentLoadingViewState
import me.kyd3snik.demo.lce.ContentViewState
import me.kyd3snik.demo.lce.ErrorViewState
import me.kyd3snik.demo.lce.LoadingViewState
import me.kyd3snik.demo.lce.ViewState

interface ViewStateRenderer<in T : ViewState<*>> {

    fun render(parent: ViewGroup, viewState: T)
}

class LoadingViewStateRenderer : ViewStateRenderer<LoadingViewState> {

    private var viewBinding: ViewStateLoadingBinding? = null

    override fun render(parent: ViewGroup, viewState: LoadingViewState) {
        val viewBinding = viewBinding ?: ViewStateLoadingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .also { viewBinding -> this.viewBinding = viewBinding }

        parent.removeAllViews()
        parent.addView(viewBinding.root)
    }
}

class ContentLoadingViewStateRenderer : ViewStateRenderer<ContentLoadingViewState> {

    private var viewBinding: ViewStateLoadingBinding? = null

    override fun render(parent: ViewGroup, viewState: ContentLoadingViewState) {
        val viewBinding = viewBinding ?: ViewStateLoadingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .also { viewBinding -> this.viewBinding = viewBinding }

        parent.addView(viewBinding.root)
    }
}

class ErrorViewStateRenderer(private val fallbackRetry: () -> Unit = {}) : ViewStateRenderer<ErrorViewState> {

    private var viewBinding: ViewStateErrorBinding? = null

    override fun render(parent: ViewGroup, viewState: ErrorViewState) {
        val viewBinding = viewBinding ?: ViewStateErrorBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .also { viewBinding -> this.viewBinding = viewBinding }

        parent.removeAllViews()
        parent.addView(viewBinding.root)
        viewBinding.errorMessage.text = viewState.error.message
        viewBinding.errorMessage.setOnClickListener { (viewState.retry ?: fallbackRetry).invoke() }
    }
}

class CompositeViewStateRenderer(
    private val loadingViewStateRenderer: ViewStateRenderer<LoadingViewState> = LoadingViewStateRenderer(),
    private val contentLoadingViewStateRenderer: ViewStateRenderer<ContentLoadingViewState> = ContentLoadingViewStateRenderer(),
    private val errorViewStateRenderer: ViewStateRenderer<ErrorViewState> = ErrorViewStateRenderer(),
) : ViewStateRenderer<CompositeViewState> {
    override fun render(parent: ViewGroup, viewState: CompositeViewState) {
        for (state in viewState.viewStates) {
            when (state) {
                is ErrorViewState -> {
                    errorViewStateRenderer.render(parent, state)
                    break
                }

                is LoadingViewState -> {
                    loadingViewStateRenderer.render(parent, state)
                    break
                }

                is ContentLoadingViewState -> {
                    contentLoadingViewStateRenderer.render(parent, state)
                    break
                }
            }
        }
    }

}