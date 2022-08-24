package com.han.my_friend_kim_jung_san.data.remote.auth

import android.util.Log
import com.han.my_friend_kim_jung_san.ApplicationClass.Companion.retrofit
import com.han.my_friend_kim_jung_san.data.entity.Accounts
import com.han.my_friend_kim_jung_san.data.entity.Login
import com.han.my_friend_kim_jung_san.ui.login.LoginView
import com.han.my_friend_kim_jung_san.ui.signup.SignUpView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthService {
    fun login(loginView: LoginView, login:Login){
        val loginService = retrofit.create(AuthRetrofitInterface::class.java)
        loginService.login(login).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.i("login", response.toString())
                val resp = response.body()!!
                when(resp.statusCode){
                    200 -> loginView.onLoginSuccess()
                    201 -> loginView.onRegisterSuccess()
                    else -> loginView.onLoginFailure(resp.statusCode!!, resp.message!!)
                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
            }

        })
    }
    fun signUp(signUpView: SignUpView, userId: String ,accounts: Accounts){
        val signUpService = retrofit.create(AuthRetrofitInterface::class.java)
        signUpService.signUp(userId, accounts).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.i("signup", response.toString())
                val resp = response.body()!!
                when(resp.statusCode){
                    200 -> signUpView.onSignUpSuccess()
                    else -> signUpView.onSignUpFailure(resp.statusCode!!, resp.message!!)
                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {

            }

        })
    }
}