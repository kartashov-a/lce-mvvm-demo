package me.kyd3snik.demo.simple

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.kyd3snik.demo.lce.Request
import me.kyd3snik.demo.lce.asRequest

class SimpleFeatureViewModel(
    private val repository: SimpleFeatureRepository
) : ViewModel() {

    fun fetchData(): Observable<Request<String>> =
        repository.fetchData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .asRequest()
}