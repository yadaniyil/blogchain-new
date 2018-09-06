package com.yadaniil.blogchain.util

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.reactivex.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemService





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

fun View.hideKeyboard(context: Context) {
    val imm = context.getSystemService(
            Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm!!.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}