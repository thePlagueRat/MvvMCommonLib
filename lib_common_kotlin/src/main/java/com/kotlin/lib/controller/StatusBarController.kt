package com.kotlin.lib.controller

import android.app.Activity
import com.kotlin.lib.R
import com.kotlin.lib.config.Config
import com.kotlin.lib.entity.StatusBarParam
import com.kotlin.lib.entity.TitleParam
import com.kotlin.lib.interfaces.IGlobalConfig
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar

/**
 * 状态栏处理器
 */
class StatusBarController(
    private val activity: Activity,
    private val iGlobalConfig: IGlobalConfig,
    private val title: TitleParam?,
    private val statusBar: StatusBarParam?
) {

    fun checkStatusBar() {
        //状态栏注解设置为不可用
        if (statusBar != null && statusBar.enabled) {
            return
        }
        activity.immersionBar {
            if (statusBar == null) {
                statusBarView(R.id.top_view)
                autoStatusBarDarkModeEnable(true, 0.2f)
                //设置状态栏颜色
                if (title == null || title.backgroundColor == Config.NO_ASSIGNMENT) {
                    statusBarColor(iGlobalConfig.getTitleBackground())
                } else {
                    statusBarColor(title.backgroundColor)
                }
            } else {
                checkStatusBarHide(this)
            }
        }
    }

    /**
     * 检查状态栏是否隐藏
     */
    private fun checkStatusBarHide(immersionBar: ImmersionBar) {
        if (statusBar!!.hide) {
            //状态栏隐藏
            immersionBar.hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
        } else {
            checkStatusBarTransparent(immersionBar)
        }
    }

    /**
     * 检查状态栏是否透明
     */
    private fun checkStatusBarTransparent(immersionBar: ImmersionBar) {
        if (statusBar!!.transparent) {
            immersionBar.transparentStatusBar()
        } else {
            immersionBar.statusBarView(R.id.top_view)
            immersionBar.autoStatusBarDarkModeEnable(true, 0.2f)
            immersionBar.statusBarColor(iGlobalConfig.getTitleBackground())
        }
    }
}