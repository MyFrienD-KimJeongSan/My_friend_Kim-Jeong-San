package com.han.my_friend_kim_jung_san

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.service.autofill.Validators.not
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.han.my_friend_kim_jung_san.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity(){
    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupCompleteBtn.setOnClickListener {
            signUp()
            finish()
        }

        binding.signupToSigninBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun getUser() : User {
        val name : String = binding.signupNameEditText.text.toString()
        val email : String = binding.signupEmailEditText.text.toString()
        val pwd : String = binding.signupPwdEditText.text.toString()
        val bank : String? = binding.signupBankEditText.text.toString()
        val account : Int? = binding.signupAccountNumberEditText.text.toString().toInt()

        return User(name, email, pwd, bank, account)
    }

    private fun signUp() {
        if (binding.signupNameEditText.text.toString().isEmpty()) {
            Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signupEmailEditText.text.toString().isEmpty() || !binding.signupEmailEditText.text.toString().contains('@')) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signupPwdEditText.text.toString() != binding.signupCheckPwdEditText.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val userDB = UserDatabase.getInstance(this)!!
        userDB.userDao().insert(getUser())

        val user = userDB.userDao().getUsers()
    }
}
