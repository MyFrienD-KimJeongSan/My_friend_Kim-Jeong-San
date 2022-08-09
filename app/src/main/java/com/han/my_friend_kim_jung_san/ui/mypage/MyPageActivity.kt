package com.han.my_friend_kim_jung_san.ui.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.han.my_friend_kim_jung_san.databinding.ActivityMyPageBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity

class MyPageActivity : BaseActivity<ActivityMyPageBinding>(ActivityMyPageBinding::inflate) {
    override fun initAfterBinding() {
        init()
    }
    private fun init(){
        binding.backArrowIBtn.setOnClickListener {
            finish()
        }
    }
}