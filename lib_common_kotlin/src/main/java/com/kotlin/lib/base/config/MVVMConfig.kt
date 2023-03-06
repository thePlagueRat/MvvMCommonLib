package com.kotlin.lib.base.config

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.kotlin.lib.R
import com.kotlin.lib.databinding.ViewRootBinding
import com.kotlin.lib.interfaces.IGlobalConfig

object MVVMConfig {
    var globalConfig: IGlobalConfig = DefaultConfig()

    fun initActivityConfig(globalConfig: IGlobalConfig) {
        this.globalConfig = globalConfig
    }


}

class DefaultConfig : IGlobalConfig {
    override fun getTitleHeight(): Int {
        return R.dimen.title_bar_height
    }

    override fun getTitleBackIcon(): Int {
        return R.drawable.ico_back
    }

    override fun getTitleBackground(): Int {
        return R.color.colorPrimary
    }

    override fun getTitleTextColor(): Int {
        return R.color.white
    }

    override fun isShowTitleLine(): Boolean {
        return true
    }

    override fun getTitleLineColor(): Int {
        return R.color.color_black
    }

    override fun getLoadingColor(): Int {
        return R.color.color_black
    }

    override fun getLoadingBackground(): Int {
        return R.color.white
    }

    override fun getRecyclerEmptyBanding(parent: ViewGroup): ViewBinding {
        return ViewRootBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun getPageSize(): Int {
        return 18
    }

    override fun getStartPageIndex(): Int {
        return 0
    }

    override fun getDefaultLoadingDialog(activity: Activity): Dialog? {
        return null
    }

    override fun showLoadDialog(dialog: Dialog, content: String) {
        dialog.show()
    }

}