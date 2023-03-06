package com.kotlin.lib.manager

import android.view.View
import androidx.annotation.StringRes
import com.kotlin.lib.R
import com.common.kotlin.utils.ext.setBackgroundResource
import com.common.kotlin.utils.ext.setMargin
import com.common.kotlin.utils.ext.setTextColorRes
import com.google.android.material.snackbar.Snackbar

object SnackbarManager {
    fun show(view: View, text: CharSequence) {
        val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundResource(R.drawable.snackbar_bg)
        snackbar.setTextColorRes(R.color.color_black)
        snackbar.setMargin(20, 0, 20, 20)
        snackbar.show()
    }

    fun show(view: View, @StringRes textRes: Int) {
        val snackbar = Snackbar.make(view, textRes, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundResource(R.drawable.snackbar_bg)
        snackbar.setTextColorRes(R.color.color_black)
        snackbar.setMargin(20, 0, 20, 20)
        snackbar.show()
    }
}