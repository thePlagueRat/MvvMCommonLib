package com.kotlin.lib.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 */
data class TitleMenuParam(
        /**
         * 右边第一个按钮图标
         */
        @DrawableRes val rightFirstDrawable:Int = -1,
        /**
         * 右边第一个按钮文字
         */
        @StringRes val rightFirstText:Int = -1,
        /**
         * 右边第二个按钮图标
         */
        @DrawableRes val rightSecondDrawable:Int = -1,
        /**
         * 右边第二个按钮文字
         */
        @StringRes val rightSecondText:Int = -1
)