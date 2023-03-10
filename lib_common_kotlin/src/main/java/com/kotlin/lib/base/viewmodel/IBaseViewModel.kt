package com.kotlin.lib.base.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.rxjava3.disposables.Disposable

/**
 * @author litao
 * date 2021/9/13
 * description:
 */
interface IBaseViewModel : LifecycleObserver {
    /**
     * 添加disposable到CompositeDisposable
     * @param disposable Disposable
     */
    fun add(disposable: Disposable)

    /**
     * 从CompositeDisposable中删除一个disposable
     * @param disposable Disposable
     */
    fun remove(disposable: Disposable)

    /**
     * 此函数可以监听Activity的全部Event事件
     * @param owner
     * @param event
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()
}