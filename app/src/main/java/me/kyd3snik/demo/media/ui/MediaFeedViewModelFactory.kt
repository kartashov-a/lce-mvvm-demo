package me.kyd3snik.demo.media.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.kyd3snik.demo.media.data.MediaFeedRepository

class MediaFeedViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MediaFeedViewModel(MediaFeedRepository()) as T
    }
}