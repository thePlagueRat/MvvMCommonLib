package com.kotlin.lib.bean

data class LoginResInfo(
    val appkey: String,
    val secret: String,
    val token: String,
    val userInfo: UserInfo
)

data class UserInfo(
    val accountType: Int,
    val areaCode: String,
    val birthday: String,
    val certificateNo: String,
    val certificateType: Int,
    val email: String,
    val empType: Int,
    val headUrl: String,
    val identityAuthStatus: Int,
    val identityAuthType: Int,
    val mobile: String,
    val nickName: String,
    val nube: String,
    val registerStatus: Int,
    val sex: Int,
    val workNationality: String
)