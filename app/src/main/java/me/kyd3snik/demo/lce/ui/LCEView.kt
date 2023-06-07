package me.kyd3snik.demo.lce.ui

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import me.kyd3snik.demo.lce.ContentViewState
import me.kyd3snik.demo.lce.LoadingViewState
import me.kyd3snik.demo.lce.ViewState
import kotlin.reflect.KClass

interface LCEView {

    fun <T : ViewState<*>> registerRenderer(viewState: KClass<T>, renderer: ViewStateRenderer<T>)

    fun <T : ViewState<*>> unregisterRenderer(viewState: KClass<T>)

    fun <T : Any> showViewState(viewState: ViewState<T>)
}

open class LCEFragment : Fragment(), LCEView {

    private val renderers = mutableMapOf<KClass<out ViewState<*>>, ViewStateRenderer<*>>()
    private val rootView = FrameLayout(requireContext())

    //TODO: use for content
    private val contentContainer = FrameLayout(requireContext())

    override fun <T : ViewState<*>> registerRenderer(viewState: KClass<T>, renderer: ViewStateRenderer<T>) {
        renderers[viewState] = renderer
    }

    override fun <T : ViewState<*>> unregisterRenderer(viewState: KClass<T>) {
        renderers.remove(viewState)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> showViewState(viewState: ViewState<T>) {
        val renderer: ViewStateRenderer<ViewState<T>>? = renderers[viewState::class] as? ViewStateRenderer<ViewState<T>>
        renderer?.render(rootView, viewState)

        if (viewState is ContentViewState<T>) {

            rootView.removeAllViews()
            rootView.addView(contentContainer)
            showContent(viewState.data)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerRenderer(LoadingViewState::class, LoadingViewStateRenderer())
    }
}