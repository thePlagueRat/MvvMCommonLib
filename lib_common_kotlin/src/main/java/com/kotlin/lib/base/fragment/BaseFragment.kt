package com.kotlin.lib.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.kotlin.lib.base.activity.BaseActivity
import com.kotlin.lib.base.config.MVVMConfig
import com.kotlin.lib.controller.LoadingViewController
import com.kotlin.lib.databinding.ViewRootBinding
import com.kotlin.lib.manager.SnackbarManager
import java.lang.reflect.ParameterizedType

/**
 * Fragment封装基类
 */
open class BaseFragment<VB : ViewBinding> : Fragment() {
    protected val bodyBinding: VB by lazy {
        var type = javaClass.genericSuperclass
        var vbClass: Class<VB> = (type as ParameterizedType).actualTypeArguments[0] as Class<VB>
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.invoke(this, layoutInflater) as VB
    }

    private val loadingViewController: LoadingViewController by lazy {
        LoadingViewController(requireActivity(), MVVMConfig.globalConfig, rootBinding)
    }

    private val rootBinding: ViewRootBinding by lazy {
        ViewRootBinding.inflate(layoutInflater)
    }

    fun getRootBanding(): ViewRootBinding {
        return rootBinding
    }

    fun baseActivity(): BaseActivity<*>? {
        if (activity is BaseActivity<*>) {
            return activity as BaseActivity<*>
        }
        return null
    }

    @CallSuper
    @Nullable
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        rootBinding.layoutBody.removeView(bodyBinding.root)
        rootBinding.layoutBody.addView(
                bodyBinding.root,
                0,
                ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        )
        return rootBinding.root
    }

    fun snackBar(text: CharSequence) {
        SnackbarManager.show(bodyBinding.root, text)
    }

    fun snackBar(@StringRes textRes: Int) {
        SnackbarManager.show(bodyBinding.root, textRes)
    }

    fun loadingView() {
        loadingViewController.loadingView()
    }

    fun loadingDialog(content: String = "") {
        loadingViewController.loadingDialog(content)
    }

    fun hideLoading() {
        loadingViewController.hideLoading()
    }
}
