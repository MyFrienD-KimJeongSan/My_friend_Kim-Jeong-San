package com.han.my_friend_kim_jung_san.ui.login


interface LoginView {
    fun onLoginSuccess()
    fun onRegisterSuccess()
    fun onLoginFailure(code: Int, message: String)
}