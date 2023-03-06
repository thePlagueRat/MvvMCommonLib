package com.kotlin.lib.base.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.gyf.immersionbar.ktx.immersionBar
import com.kotlin.lib.R
import com.kotlin.lib.base.config.MVVMConfig
import com.kotlin.lib.controller.LoadingViewController
import com.kotlin.lib.controller.TitleBarController
import com.kotlin.lib.databinding.ViewRootBinding
import com.kotlin.lib.entity.TitleParam
import com.kotlin.lib.manager.SnackbarManager
import java.lang.reflect.ParameterizedType

/**
 *
 */
open class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {
    protected val bodyBinding: VB by lazy {
        var type = javaClass.genericSuperclass
        var vbClass: Class<VB> = (type as ParameterizedType).actualTypeArguments[0] as Class<VB>
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.invoke(this, layoutInflater) as VB
    }
    private val rootBinding: ViewRootBinding by lazy {
        ViewRootBinding.inflate(layoutInflater)
    }

    private lateinit var loadingViewController: LoadingViewController

    private lateinit var titleBarController: TitleBarController

    fun initLoadingViewController(loadingViewController: LoadingViewController) {
        this.loadingViewController = loadingViewController
    }

    fun getRootBanding(): ViewRootBinding {
        return rootBinding
    }

    @CallSuper
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.setContentView(rootBinding.root)
        super.onCreate(savedInstanceState)
        setContentView(bodyBinding.root)
        loadingViewController = LoadingViewController(this, MVVMConfig.globalConfig, rootBinding)
    }

    fun initTitleBar(
        isShow: Boolean = true,
        title: String = "",
        isShowLine: Boolean = false,
        @ColorRes backgroundColor: Int = R.color.color_white,
        @ColorRes titleColor: Int = R.color.color_black,
        showLeftIcon: Boolean = true,
        @DrawableRes leftIcon: Int = R.drawable.ico_back,
        @DrawableRes rightFirstIcon: Int = -1,
        rightFirstText: String = "",
        onRightClick: (() -> Unit)? = null
    ) {

        if (isShow) {
            rootBinding.titleBarViewStub.viewStub!!.inflate()
            val view = rootBinding.layoutRoot.findViewById<RelativeLayout>(R.id.title_bar)
            val titleParams = TitleParam(
                value = -1,
                backgroundColor,
                titleColor,
                leftIcon,
                title,
                isShowLine,
                showLeftIcon,
                rightFirstIcon,
                rightFirstText,
                onRightClick = onRightClick
            )
            titleBarController = TitleBarController(this, MVVMConfig.globalConfig, titleParams)
            titleBarController.initTitleBar(view)

            immersionBar {
                fitsSystemWindows(true).statusBarColor(backgroundColor).statusBarDarkFont(true)
                    .navigationBarColor(R.color.white)
                    .navigationBarDarkIcon(true)
                    .keyboardEnable(true)
            }
        }

    }

    override fun setContentView(view: View?) {
        rootBinding.layoutBody.let {
            it.addView(
                view,
                0,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
    }

    /**
     * 改变title文字
     */
    fun updateTitle(title: String) {
        var titleText = rootBinding.root.findViewById<TextView>(R.id.title_text)
        titleText.text = title
    }

    /**
     * 改变title文字
     */
    fun updateTitle(@StringRes title: Int) {
        var titleText = rootBinding.root.findViewById<TextView>(R.id.title_text)
        titleText.setText(title)
    }

    fun snackBar(text: CharSequence) {
        SnackbarManager.show(rootBinding.layoutBody, text)
    }

    fun snackBar(@StringRes textRes: Int) {
        SnackbarManager.show(rootBinding.layoutBody, textRes)
    }

    fun loadingDialog(content: String = "") {
        loadingViewController.loadingDialog(content)
    }


    fun loadingView() {
        loadingViewController.loadingView()
    }

    fun hideLoadingView() {
        loadingViewController.hideLoading()
    }

    open fun startActivity(clz: Class<*>?) {
        startActivity(Intent(this, clz))
    }

    fun closeActivity() {
        finish()
    }
}
