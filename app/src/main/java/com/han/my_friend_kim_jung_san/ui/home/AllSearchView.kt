package com.han.my_friend_kim_jung_san.ui.home

import com.han.my_friend_kim_jung_san.data.entity.Data

interface AllSearchView {
    fun onAllSearchSuccess(data: List<Data>?)
    fun onAllSearchFailure(code: Int?, message: String?)
}