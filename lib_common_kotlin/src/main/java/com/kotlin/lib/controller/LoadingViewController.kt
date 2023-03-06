package com.kotlin.lib.controller

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import com.catchpig.loading.view.LoadingView
import com.kotlin.lib.R
import com.kotlin.lib.databinding.ViewRootBinding
import com.kotlin.lib.interfaces.IGlobalConfig
import com.gyf.immersionbar.ImmersionBar

/**
 *
 **/
class LoadingViewController(
        private val activity: Activity,
        private val iGlobalConfig: IGlobalConfig,
        private val rootBinding: ViewRootBinding
) {
    private var dialog: Dialog? = null
    private var isLoadingInflate = false
    private lateinit var loadingFrame: FrameLayout
    private lateinit var loadingView: LoadingView
    fun loadingView() {
        rootBinding.layoutBody.run {
            if (isLoadingInflate) {
                loadingFrame.visibility = View.VISIBLE
                loadingFrame.setBackgroundResource(iGlobalConfig.getLoadingBackground())
                loadingView.setLoadColor(iGlobalConfig.getLoadingColor())
            } else {
                //setOnInflateListener监听器一定要在inflate()之前,不然会报空指针
                rootBinding.loadingViewStub.setOnInflateListener { _, view ->
                    isLoadingInflate = true
                    loadingFrame = view.findViewById(R.id.loading_frame);
                    loadingFrame.visibility = View.VISIBLE
                    loadingFrame.setBackgroundResource(iGlobalConfig.getLoadingBackground())
                    loadingView = view.findViewById(R.id.loading_view)
                    loadingView.setLoadColor(iGlobalConfig.getLoadingColor())
                }
                rootBinding.loadingViewStub.viewStub!!.inflate()
            }
        }
    }

    fun loadingDialog(content: String) {
        if (dialog == null) {
            if (iGlobalConfig.getDefaultLoadingDialog(activity) != null) {
                dialog = iGlobalConfig.getDefaultLoadingDialog(activity)
            } else {
                dialog = Dialog(activity, R.style.loading_dialog_theme)
                dialog?.run {
                    setCancelable(false)
                    setContentView(R.layout.layout_loading)
                    val loadingFrame = findViewById<FrameLayout>(R.id.loading_frame);
                    val loadingView = findViewById<LoadingView>(R.id.loading_view)
                    loadingFrame.visibility = View.VISIBLE
                    loadingView.setLoadColor(iGlobalConfig.getLoadingColor())
                    ImmersionBar.with(activity, this).transparentBar().init()
                    show()
                    window?.run {
                        var layoutParams = attributes
                        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
                        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
                        decorView.setPadding(0, 0, 0, 0)
                        attributes = layoutParams
                    }
                }
            }
        }

        iGlobalConfig.showLoadDialog(dialog!!, content)
    }

    fun hideLoading() {
        dialog?.let {
            it.dismiss()
        }
        if (isLoadingInflate) {
            loadingFrame.visibility = View.GONE
        }
    }
}