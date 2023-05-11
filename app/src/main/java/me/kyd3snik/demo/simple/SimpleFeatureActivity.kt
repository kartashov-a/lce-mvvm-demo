package me.kyd3snik.demo.simple

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import io.reactivex.disposables.Disposable
import me.kyd3snik.demo.databinding.ViewSimpleFeatureBinding
import me.kyd3snik.demo.lce.ui.LCEActivity

class SimpleFeatureActivity : LCEActivity() {

    private val viewModel: SimpleFeatureViewModel by viewModels {
        SimpleFeatureViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ViewSimpleFeatureBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(viewBinding.root)

        viewModel.fetchData()
            .subscribe { request ->
                showRequest(request) { data ->
                    viewBinding.content.text = data
                }
            }.disposeOnDestroy()
    }
}

fun Disposable.disposeOnDestroy() {
    // doesn't matter
}