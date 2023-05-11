package me.kyd3snik.demo.lce

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single


fun <T : Any> Observable<T>.asRequest(): Observable<Request<T>> = this
    .map<Request<T>> { data -> Request.Content(data) }
    .startWith(Request.Loading)
    .onErrorReturn { error -> Request.Error(error) }

fun <T : Any> Single<T>.asRequest(): Observable<Request<T>> = toObservable().asRequest()

fun <T : Any> Maybe<T>.asRequest(): Observable<Request<T?>> = this
    .map<Request<T?>> { data -> Request.Content(data) }
    .switchIfEmpty(Single.just(Request.Content(null)))
    .toObservable()
    .startWith(Request.Loading)
    .onErrorReturn { error -> Request.Error(error) }
