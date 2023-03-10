package com.kotlin.lib.config

import com.google.gson.GsonBuilder

/**
 * 配置
 */
object Config {
    //时间格式化规则
    const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    //没有赋值
    const val NO_ASSIGNMENT = -1

    val gson = GsonBuilder().setDateFormat(DATE_FORMAT).create()
}