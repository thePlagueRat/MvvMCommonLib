package com.kotlin.lib.provider

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.kotlin.lib.lifecycle.ActivityLifeCycleCallbacksImpl
import com.kotlin.lib.manager.ContextManager

/**
 * @author catchpig
 * @date 2019/10/21 00:21
 */
class KotlinMvpContentProvider : ContentProvider() {

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun onCreate(): Boolean {
        val applicationContext = context!!.applicationContext
        ContextManager.context = applicationContext
        val application = applicationContext as Application
        application.registerActivityLifecycleCallbacks(ActivityLifeCycleCallbacksImpl())
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return ""
    }
}