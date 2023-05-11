package me.kyd3snik.demo.media.data

import io.reactivex.Single
import java.util.concurrent.TimeUnit


class MediaFeedRepository {

    fun fetchTopics(): Single<List<Topic>> = Single.timer(3, TimeUnit.SECONDS)
        .map {
            listOf(
                Topic("1", "All"),
                Topic("2", "Video"),
                Topic("3", "Community"),
                Topic("4", "Sponsors"),
            )
        }

    fun fetchPosts(topicId: String = "1"): Single<List<Post>> = Single.timer(3, TimeUnit.SECONDS)
        .map {
            if(topicId == "4") error("Internal server error. Try again later")
            listOf(
                Post("0", "Some text", "Some text", "2"),
                Post("1", "Some text", "Some text", "2"),
                Post("2", "Some text", "Some text", "3"),
                Post("3", "Some text", "Some text", "3"),
            ).filter { topicId == "1" || it.topicId == topicId }
        }
}