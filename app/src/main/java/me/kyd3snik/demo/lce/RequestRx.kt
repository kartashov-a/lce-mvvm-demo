package me.kyd3snik.demo.lce

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single


fun <T : Any> Observable<T>.toViewState(): Observable<ViewState<T>> = this
    .map<ViewState<T>> { data -> ContentViewState(data) }
    .startWith(LoadingViewState)
    .onErrorReturn { error -> ErrorViewState(error) }

fun <T : Any> Single<T>.toViewState(): Observable<ViewState<T>> = toObservable().toViewState()

fun <T : Any> Maybe<T>.toViewState(): Observable<ViewState<T?>> = this
    .map<ViewState<T?>> { data -> ContentViewState(data) }
    .switchIfEmpty(Single.just(ContentViewState(null)))
    .toObservable()
    .startWith(LoadingViewState)
    .onErrorReturn { error -> ErrorViewState(error) }
