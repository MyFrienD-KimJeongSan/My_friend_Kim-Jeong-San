package com.han.my_friend_kim_jung_san.ui.signup

interface SignUpView {
    fun onSignUpSuccess()
    fun onSignUpFailure(code: Int, message: String)
}