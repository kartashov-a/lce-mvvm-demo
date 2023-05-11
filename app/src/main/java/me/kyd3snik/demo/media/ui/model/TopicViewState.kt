package me.kyd3snik.demo.media.ui.model

import me.kyd3snik.demo.media.data.Topic

data class TopicViewState(
    val id: String,
    val title: String,
    val isSelected: Boolean
) {

    companion object {

        fun map(topics: List<Topic>) = topics.mapIndexed { index, topic -> map(topic, isSelected = index == 0) }

        fun map(topic: Topic, isSelected: Boolean = false): TopicViewState =
            TopicViewState(topic.id, topic.title, isSelected)
    }
}