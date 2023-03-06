package com.kotlin.lib.entity

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 */
data class TitleParam(
    /**
     * 标题内容
     */
    @StringRes val value: Int,
    /**
     * 标题背景色
     */
    @ColorRes val backgroundColor: Int = -1,
    /**
     * 标题文字颜色
     */
    @ColorRes val textColor: Int = -1,
    /**
     * 返回按钮图标
     */
    @DrawableRes val backIcon: Int = -1,

    val titleStr: String,

    val showLine: Boolean,

    val showLeftIcon: Boolean,

    @DrawableRes val rightFirstIcon: Int = -1,

    val rightFirstText: String = "",

    val onRightClick: (() -> Unit)? = null,

    /**
     * 右边文字颜色
     */
    @ColorRes val rightTextColor: Int = -1,

)