package com.han.my_friend_kim_jung_san.ui.account

import com.han.my_friend_kim_jung_san.data.entity.Data
import com.han.my_friend_kim_jung_san.data.entity.UserData

interface AccountView {
    fun onUserSearchSuccess(userData: UserData)
    fun onUserSearchFailure()
}