package me.kyd3snik.demo.simple

import io.reactivex.Single
import java.util.concurrent.TimeUnit

class SimpleFeatureRepository {

    fun fetchData(): Single<String> =
        Single.timer(3, TimeUnit.SECONDS)
            .map { "Data from remote source" }
}