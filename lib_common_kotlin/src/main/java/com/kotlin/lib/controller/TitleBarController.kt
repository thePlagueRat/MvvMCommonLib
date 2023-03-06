package com.kotlin.lib.controller

import android.app.Activity
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.common.kotlin.utils.ext.TAG
import com.kotlin.lib.R
import com.kotlin.lib.config.Config
import com.kotlin.lib.databinding.LayoutTitleBarBinding
import com.kotlin.lib.entity.TitleMenuParam
import com.kotlin.lib.entity.TitleParam
import com.kotlin.lib.interfaces.IGlobalConfig
import com.common.kotlin.utils.ext.colorResToInt

/**
 * 标题栏处理器
 */
class TitleBarController(
    private val activity: Activity,
    private val iGlobalConfig: IGlobalConfig,
    private val title: TitleParam
) : View.OnClickListener {

    fun initTitleBar(view: View) {

        val titleBarBinding = DataBindingUtil.bind<LayoutTitleBarBinding>(view)
        titleBarBinding?.titleBar.let {
            val titleBarHeight =
                activity.resources.getDimensionPixelOffset(iGlobalConfig.getTitleHeight())
            it?.post {
                var layoutParams = it.layoutParams
                layoutParams.height = titleBarHeight
                it.layoutParams = layoutParams
            }
            it?.visibility = View.VISIBLE
            initListener(titleBarBinding)
            //设置背景色
            drawBackground(it)
        }
        //设置文字颜色
        drawTextColor(titleBarBinding)
        //设置返回按钮图标
        if (title.showLeftIcon) {
            titleBarBinding?.backIcon?.visibility = View.VISIBLE
            drawBackIcon(titleBarBinding?.backIcon)
        } else {
            titleBarBinding?.backIcon?.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(title.titleStr)) {
            titleBarBinding?.titleText?.setText(title.titleStr)
        }
        if (title.rightFirstIcon > 0) {
            titleBarBinding?.rightFirstDrawable?.visibility = View.VISIBLE
            titleBarBinding?.rightFirstDrawable?.setImageResource(title.rightFirstIcon)
        }
        if (!TextUtils.isEmpty(title.rightFirstText)) {
            titleBarBinding?.rightFirstText?.visibility = View.VISIBLE
            titleBarBinding?.rightFirstText?.text = title.rightFirstText
        }

        drawLine(titleBarBinding?.line)
    }

    private fun drawLine(line: View?) {
        if (title.showLine) {
            line?.visibility = View.VISIBLE
            val lineColor = iGlobalConfig.getTitleLineColor()
            if (lineColor != 0) {
                line?.setBackgroundResource(lineColor)
            }
        } else {
            line?.visibility = View.GONE
        }
    }

    /**
     * 绘制右边按钮
     */
    private fun drawTitleMenu(titleBar: FrameLayout, titleMenu: TitleMenuParam) {
        //右边第一个文字按钮
        titleBar.findViewById<TextView>(R.id.rightFirstText).run {
            if (titleMenu.rightFirstText != Config.NO_ASSIGNMENT) {
                visibility = View.VISIBLE
                setText(titleMenu.rightFirstText)
            } else {
                visibility = View.GONE
            }
        }
        //右边第二个文字按钮
        titleBar.findViewById<TextView>(R.id.rightSecondText).run {
            if (titleMenu.rightSecondText != Config.NO_ASSIGNMENT) {
                visibility = View.VISIBLE
                setText(titleMenu.rightSecondText)
            } else {
                visibility = View.GONE
            }
        }
        //右边第一个图标按钮
        titleBar.findViewById<ImageView>(R.id.rightFirstDrawable).run {
            if (titleMenu.rightFirstDrawable != Config.NO_ASSIGNMENT) {
                visibility = View.VISIBLE
                setImageResource(titleMenu.rightFirstDrawable)
            } else {
                visibility = View.GONE
            }
        }
        //右边第二个图标按钮
        titleBar.findViewById<ImageView>(R.id.rightSecondDrawable).run {
            if (titleMenu.rightSecondDrawable != Config.NO_ASSIGNMENT) {
                visibility = View.VISIBLE
                setImageResource(titleMenu.rightSecondDrawable)
            } else {
                visibility = View.GONE
            }
        }
    }

    /**
     * 初始化监听器
     */
    private fun initListener(titleBarBinding: LayoutTitleBarBinding?) {
        titleBarBinding?.backIcon?.setOnClickListener(this)
        if (title.onRightClick != null) {
            Log.i(TAG, "onRightClick is not null")
            titleBarBinding?.llRight?.setOnClickListener(this)
        } else {
            Log.i(TAG, "onRightClick is null")
        }
    }

    override fun onClick(view: View?) {
        Log.i(TAG, "onClick>>id:" + view?.id)
        when (view!!.id) {
            //返回
            R.id.back_icon -> activity.finish()
            R.id.llRight -> {
                Log.i(TAG, "onClick>>R.id.llRight")
                title.onRightClick?.invoke()
            }
        }
    }

    /**
     * 设置背景色
     */
    private fun drawBackground(titleBar: RelativeLayout?) {
        val backgroundColor = if (title!!.backgroundColor == Config.NO_ASSIGNMENT) {
            iGlobalConfig.getTitleBackground()
        } else {
            title.backgroundColor
        }
        titleBar?.setBackgroundResource(backgroundColor)
    }

    /**
     * 设置文字颜色
     */
    private fun drawTextColor(titleBarBinding: LayoutTitleBarBinding?) {
        var textColor = if (title!!.textColor == Config.NO_ASSIGNMENT) {
            activity.colorResToInt(iGlobalConfig.getTitleTextColor())
        } else {
            activity.colorResToInt(title.textColor)
        }
        titleBarBinding?.titleText?.setTextColor(textColor)
        if (title.rightTextColor > 0) {
            val rightTextColor = activity.colorResToInt(title.rightTextColor)
            titleBarBinding?.rightFirstText?.setTextColor(rightTextColor)
            titleBarBinding?.rightSecondText?.setTextColor(rightTextColor)
        }
    }

    /**
     * 设置返回按钮图标
     */
    private fun drawBackIcon(backIcon: ImageView?) {
        if (title!!.backIcon == Config.NO_ASSIGNMENT) {
            backIcon?.setImageResource(iGlobalConfig.getTitleBackIcon())
        } else {
            backIcon?.setImageResource(title.backIcon)
        }
    }
}