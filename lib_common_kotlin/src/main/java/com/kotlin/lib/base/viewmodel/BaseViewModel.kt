package com.kotlin.lib.base.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.kotlin.lib.entity.ShowDialogParam
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 *
 */
open class BaseViewModel : ViewModel(), IBaseViewModel {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    val showDialogLiveData = MutableLiveData<ShowDialogParam>()
    val showToastLiveData = MutableLiveData<String>()

    open class UiState<T>(
        val isSuccess: T? = null,
        val isError: String? = null
    )

    /**
     * 添加Disposable到CompositeDisposable
     * @param disposable Disposable
     */
    override fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    /**
     * 删除指定的Disposable
     * @param disposable Disposable
     */
    override fun remove(disposable: Disposable) {
        compositeDisposable.remove(disposable)
    }

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {

    }

    override fun onCreate() {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun <T> flowLoadingDialog(flow: Flow<T>, callback: T.() -> Unit, content: String = "") {
        Log.e(
            this@BaseViewModel::class.java.simpleName,
            "begin thread:" + Thread.currentThread().id
        )
        viewModelScope.launch(Dispatchers.Main) {
            flow.flowOn(Dispatchers.IO).onStart {
                Log.e(
                    this@BaseViewModel::class.java.simpleName,
                    "begin thread:" + Thread.currentThread().id
                )
                showDialogLiveData.postValue(ShowDialogParam(true, content))
            }.onCompletion {
                Log.e(this@BaseViewModel::class.java.simpleName, "completion")
                showDialogLiveData.postValue(ShowDialogParam(false, ""))
            }.catch { t: Throwable ->
                Log.e(this@BaseViewModel::class.java.simpleName, "error", t)
                t.printStackTrace()
            }.collect {
                Log.e(this@BaseViewModel::class.java.simpleName, "collect")
                callback(it)
            }
        }
    }

    fun showToast(msg: String) {
        showToastLiveData.value = msg
    }
}