package me.kyd3snik.demo.media.ui

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import me.kyd3snik.demo.lce.toViewState
import me.kyd3snik.demo.media.data.MediaFeedRepository
import me.kyd3snik.demo.media.ui.model.TopicViewState
import me.kyd3snik.demo.simple.disposeOnDestroy
import kotlin.reflect.KProperty


class MediaFeedViewModel(
    private val repository: MediaFeedRepository
) : ViewModel() {

    private val stateSubject = BehaviorSubject.createDefault(MediaFeedViewState())
    private var currentState: MediaFeedViewState by SubjectDelegate(stateSubject)

    fun observeState(): Observable<MediaFeedViewState> = stateSubject.hide()

    fun fetchData() {
        Observable.combineLatest(
            repository.fetchTopics()
                .map { TopicViewState.map(it) }.toViewState(),
            repository.fetchPosts().toViewState(),
            ::Pair
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { (topicsRequest, postsRequest) ->
                currentState = MediaFeedViewState(topicsRequest, postsRequest)
            }.disposeOnDestroy()
    }

    fun selectTopic(topicId: String) {
        currentState = currentState.selectTopic(topicId)
        repository.fetchPosts(topicId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toViewState()
            .subscribe { postsRequest ->
                if (!postsRequest.isLoading){
                    currentState = currentState.copy(postsViewState = postsRequest)
                }
            }.disposeOnDestroy()
    }

    fun openPost(postId: String) {
        // TODO: open something
    }

    fun refresh() {
        TODO("Implement later")
    }
    fun retry() {
        TODO("Implement later")
    }
}

class SubjectDelegate<T : Any>(private val delegate: BehaviorSubject<T>) {

    operator fun getValue(ref: Any, property: KProperty<*>): T = requireNotNull(delegate.value)

    operator fun setValue(ref: Any, property: KProperty<*>, t: T) = delegate.onNext(t)
}

