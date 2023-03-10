package com.kotlin.lib.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.kotlin.lib.manager.KTActivityManager
import com.common.kotlin.utils.ext.logd

/**
 * activity生命周期的监听
 */
class ActivityLifeCycleCallbacksImpl : Application.ActivityLifecycleCallbacks {
    companion object {
        private const val TAG = "ActivityManagerLifeCycleCallbacksImpl"
    }

    override fun onActivityPaused(activity: Activity) {
        "${activity::class.java.simpleName}->onPause".logd(TAG)
    }

    override fun onActivityStarted(activity: Activity) {
        "${activity::class.java.simpleName}->onStart".logd(TAG)
    }

    override fun onActivityDestroyed(activity: Activity) {
        "${activity::class.java.simpleName}->onDestroy".logd(TAG)
        KTActivityManager.removeActivity(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {
        "${activity::class.java.simpleName}->onSaveInstance".logd(TAG)
    }

    override fun onActivityStopped(activity: Activity) {
        "${activity::class.java.simpleName}->onStop".logd(TAG)
    }

    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        "${activity::class.java.simpleName}->onCreate".logd(TAG)
        KTActivityManager.addActivity(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        "${activity::class.java.simpleName}->onResume".logd(TAG)
    }
}