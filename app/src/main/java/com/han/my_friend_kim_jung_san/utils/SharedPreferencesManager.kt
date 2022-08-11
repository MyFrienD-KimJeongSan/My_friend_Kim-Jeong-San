package com.han.my_friend_kim_jung_san.utils

import com.han.my_friend_kim_jung_san.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.han.my_friend_kim_jung_san.ApplicationClass.Companion.mSharedPreferences


fun saveJwt(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString(X_ACCESS_TOKEN, jwtToken)

    editor.apply()
}

fun getJwt(): String? = mSharedPreferences.getString(X_ACCESS_TOKEN, null)
