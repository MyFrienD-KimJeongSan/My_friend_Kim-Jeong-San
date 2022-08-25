package com.han.my_friend_kim_jung_san.ui.signup

import com.han.my_friend_kim_jung_san.data.entity.Accounts
import com.han.my_friend_kim_jung_san.data.remote.auth.AuthService
import com.han.my_friend_kim_jung_san.databinding.ActivitySignUpBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity

import com.han.my_friend_kim_jung_san.ui.main.MainActivity

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(ActivitySignUpBinding::inflate), SignUpView{
    var uid = ""
    override fun initAfterBinding() {
        binding.NameTextView.text = this.intent.getStringExtra("name")
        binding.completeBtn.setOnClickListener {
            accountUpdate()
        }
    }


    override fun onSignUpSuccess() {
        startActivityWithClear(MainActivity::class.java)
    }

    override fun onSignUpFailure(code: Int, message: String) {
    }
    private fun accountUpdate(){
        uid = this.intent.getStringExtra("uid").toString()
        AuthService.signUp(this, uid, Accounts("${binding.bankBtn.text} ${binding.accountET.text}"))
    }
}
