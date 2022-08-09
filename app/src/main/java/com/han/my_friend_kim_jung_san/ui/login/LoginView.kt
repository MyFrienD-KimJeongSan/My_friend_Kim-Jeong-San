package com.han.my_friend_kim_jung_san.ui.login

import com.han.my_friend_kim_jung_san.data.remote.auth.Auth

interface LoginView {
    fun onLoginSuccess(auth: Auth)
    fun onLoginFailure(code: Int, message: String)
}