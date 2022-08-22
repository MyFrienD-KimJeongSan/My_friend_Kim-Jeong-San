package com.han.my_friend_kim_jung_san.ui.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.han.my_friend_kim_jung_san.data.entity.Accounts
import com.han.my_friend_kim_jung_san.data.entity.Login
import com.han.my_friend_kim_jung_san.data.remote.auth.AuthService
import com.han.my_friend_kim_jung_san.databinding.ActivityMyPageBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity
import com.han.my_friend_kim_jung_san.ui.main.MainActivity
import com.han.my_friend_kim_jung_san.ui.signup.SignUpView
import com.kakao.sdk.user.UserApiClient

class MyPageActivity : BaseActivity<ActivityMyPageBinding>(ActivityMyPageBinding::inflate), SignUpView {
    var uid = ""
    var name = ""
    override fun initAfterBinding() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("user", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i("user", "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                name = user.kakaoAccount?.profile?.nickname.toString()
                uid = user.id.toString()
                binding.name.text = user.kakaoAccount?.profile?.nickname
                binding.email.text = user.kakaoAccount?.email
            }
        }

        init()
    }
    private fun init(){
        binding.backArrowIBtn.setOnClickListener {
            finish()
        }
        binding.editTV.setOnClickListener {
            update()
        }
    }

    override fun onSignUpSuccess() {
        startActivityWithClear(MainActivity::class.java)
    }

    override fun onSignUpFailure(code: Int, message: String) {

    }
    private fun update() {
        AuthService.signUp(this, uid, Accounts("${binding.bankBtn.text} ${binding.accountET.text}"))
    }
}