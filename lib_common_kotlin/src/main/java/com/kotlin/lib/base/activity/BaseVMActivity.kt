package com.kotlin.lib.base.activity

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.kotlin.lib.BR
import com.kotlin.lib.base.adapter.RecyclerAdapter
import com.kotlin.lib.base.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType

/**
 */
abstract class BaseVMActivity<VB : ViewDataBinding, VM : BaseViewModel> : BaseActivity<VB>() {

    val viewModel: VM by lazy {
        var type = javaClass.genericSuperclass
        var modelClass: Class<VM> = (type as ParameterizedType).actualTypeArguments[1] as Class<VM>
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(modelClass)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParam()
        lifecycle.addObserver(viewModel)
        initDataBinding()
        initLoading()
        initObserve()
        initView()
        initFlow()
    }

    private fun initDataBinding() {
        bodyBinding.lifecycleOwner = this
        bodyBinding.setVariable(BR.viewModel, viewModel)
    }

    private fun initLoading() {
        viewModel.showDialogLiveData.observe(this, {
            if (it.isShow) {
                loadingDialog(it.content)
            } else {
                hideLoadingView()
            }
        })
        viewModel.showToastLiveData.observe(this, {
            if (!TextUtils.isEmpty(it))
                Toast.makeText(this, it, Toast.LENGTH_LONG);
        })
    }

    protected abstract fun initParam()
    protected abstract fun initView()
    protected abstract fun initFlow()
    open protected fun initObserve() {}

    fun <T> lifecycleFlowRefresh(
            flow: Flow<MutableList<T>>,
            recyclerAdapter: RecyclerAdapter<T, out ViewBinding>
    ) {
        lifecycleScope.launch(Dispatchers.Main) {
            flow.flowOn(Dispatchers.IO).catch {
                recyclerAdapter.updateFailed()
            }.collect {
                recyclerAdapter.autoUpdateList(it)
            }
        }
    }

    fun <T> lifecycleFlow(flow: Flow<T>, callback: T.() -> Unit) =
            lifecycleScope.launch(Dispatchers.Main) {
                flow.flowOn(Dispatchers.IO).catch { t: Throwable ->

                }.collect {
                    callback(it)
                }
            }

    fun <T> lifecycleFlowLoadingView(flow: Flow<T>, callback: T.() -> Unit) {
        lifecycleScope.launch(Dispatchers.Main) {
            flow.flowOn(Dispatchers.IO).onStart {
                loadingView()
            }.onCompletion {
                hideLoadingView()
            }.catch { t: Throwable ->

            }.collect {
                callback(it)
            }
        }
    }

    fun <T> lifecycleFlowLoadingDialog(flow: Flow<T>, callback: T.() -> Unit) {
        lifecycleScope.launch(Dispatchers.Main) {
            flow.flowOn(Dispatchers.IO).onStart {
                loadingDialog()
            }.onCompletion {
                hideLoadingView()
            }.catch { t: Throwable ->
                t.printStackTrace()
            }.collect {
                callback(it)
            }
        }
    }
}
