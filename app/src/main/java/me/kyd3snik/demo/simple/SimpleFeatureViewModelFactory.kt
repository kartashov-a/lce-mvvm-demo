package me.kyd3snik.demo.simple

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SimpleFeatureViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SimpleFeatureViewModel(SimpleFeatureRepository()) as T
    }
}