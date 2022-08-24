package com.han.my_friend_kim_jung_san.ui.home

import com.han.my_friend_kim_jung_san.data.entity.Data

interface SearchView {
    fun onSearchSuccess(data: List<Data>?)
    fun onSearchFailure(code: Int?, message: String?)
}