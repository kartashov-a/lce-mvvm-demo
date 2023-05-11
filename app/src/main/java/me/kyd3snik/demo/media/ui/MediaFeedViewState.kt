package me.kyd3snik.demo.media.ui

import me.kyd3snik.demo.lce.Request
import me.kyd3snik.demo.media.data.Post
import me.kyd3snik.demo.media.ui.model.TopicViewState

data class MediaFeedViewState(
    val topicsRequest: Request<List<TopicViewState>> = Request.Loading,
    val postsRequest: Request<List<Post>> = Request.Loading
) {

    val isLoading: Boolean = topicsRequest.isLoading || postsRequest.isLoading
    val errorOrNull: Throwable? = topicsRequest.errorOrNull ?: postsRequest.errorOrNull

    val topics: List<TopicViewState> = topicsRequest.dataOrNull.orEmpty()
    val posts = postsRequest.dataOrNull.orEmpty()

    fun selectTopic(topicId: String): MediaFeedViewState = copy(
        topicsRequest = Request.Content(topics.map { it.copy(isSelected = it.id == topicId) })
    )
}