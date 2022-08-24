package com.han.my_friend_kim_jung_san.data.remote.register

import android.service.autofill.UserData
import com.han.my_friend_kim_jung_san.ApplicationClass.Companion.retrofit
import com.han.my_friend_kim_jung_san.ui.account.AccountView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserService {
    fun searchUser(accountView: AccountView, userId: String){
        val searchUserService = retrofit.create(UserRetrofitInterface::class.java)
        searchUserService.searchUser(userId).enqueue(object : Callback<SearchUserResponse>{
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                val resp = response.body()!!
                when(resp.statusCode){
                    200 -> accountView.onUserSearchSuccess(resp.userData!!)
                    else -> accountView.onUserSearchFailure()
                }
            }
            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
            }
        })
    }
}