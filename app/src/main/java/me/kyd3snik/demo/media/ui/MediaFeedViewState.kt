package me.kyd3snik.demo.media.ui

import me.kyd3snik.demo.lce.CompositeViewState
import me.kyd3snik.demo.lce.ContentViewState
import me.kyd3snik.demo.lce.LoadingViewState
import me.kyd3snik.demo.lce.ViewState
import me.kyd3snik.demo.media.data.Post
import me.kyd3snik.demo.media.ui.model.TopicViewState

data class MediaFeedViewState(
    val topicsViewState: ViewState<List<TopicViewState>> = LoadingViewState,
    val postsViewState: ViewState<List<Post>> = LoadingViewState
) {

    val compositeViewState = CompositeViewState(topicsViewState, postsViewState)

    val isLoading: Boolean = topicsViewState.isLoading || postsViewState.isLoading
    val errorOrNull: Throwable? = topicsViewState.errorOrNull ?: postsViewState.errorOrNull

    val topics: List<TopicViewState> = topicsViewState.dataOrNull.orEmpty()
    val posts = postsViewState.dataOrNull.orEmpty()

    fun selectTopic(topicId: String): MediaFeedViewState = copy(
        topicsViewState = ContentViewState(topics.map { it.copy(isSelected = it.id == topicId) })
    )
}