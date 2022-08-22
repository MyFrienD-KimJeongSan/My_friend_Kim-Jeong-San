package com.han.my_friend_kim_jung_san.ui.meeting.invite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.han.my_friend_kim_jung_san.databinding.ActivityInviteBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity

class InviteActivity : BaseActivity<ActivityInviteBinding>(ActivityInviteBinding::inflate){
    override fun initAfterBinding() {
        binding.backIBtn.setOnClickListener {
            finish()
        }
    }
}