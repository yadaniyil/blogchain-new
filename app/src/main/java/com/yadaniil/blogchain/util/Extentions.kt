package com.yadaniil.blogchain.util

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.reactivex.*

fun <T> Flowable<T>.toLiveData() :  LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this)
}

fun <T> Observable<T>.toLiveData(backPressureStrategy: BackpressureStrategy) :  LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable(backPressureStrategy))
}

fun <T> Single<T>.toLiveData() :  LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

fun <T> Maybe<T>.toLiveData() :  LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

fun <T> Completable.toLiveData() : LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

fun View.visible(): View {
    this.visibility = View.VISIBLE
    return this
}

fun View.invisible(): View {
    this.visibility = View.INVISIBLE
    return this
}

fun View.gone(): View {
    this.visibility = View.GONE
    return this
}

fun SwipeRefreshLayout.stopRefreshing() {
    this.isRefreshing = false
}

fun SwipeRefreshLayout.startRefreshing() {
    this.isRefreshing = true
}