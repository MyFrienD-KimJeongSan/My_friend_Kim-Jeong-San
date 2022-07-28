package com.han.my_friend_kim_jung_san

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.han.my_friend_kim_jung_san.databinding.ActivityLoginBinding
import com.han.my_friend_kim_jung_san.databinding.ActivitySignUpBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginToSignupText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginCompleteBtn.setOnClickListener {
            login()
        }

        binding.loginMissingPwdText.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun login(){
        if (binding.loginEmailEditText.text.toString().isEmpty() || !binding.loginEmailEditText.text.toString().contains('@')) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.loginPwdEditText.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val email : String = binding.loginEmailEditText.text.toString()
        val pwd : String = binding.loginPwdEditText.text.toString()

        val userDB = UserDatabase.getInstance(this)!!
        val user = userDB.userDao().getUser(email, pwd)

        user?.let {
            Log.d("LOGIN_ACT/GET_USER", "userId : ${user.id}, $user")
            saveJwt(user.id)
            startMainActivity()
        }

        Toast.makeText(this, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun saveJwt(jwt:Int) {
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putInt("jwt", jwt)
        editor.apply()
    }

    private fun startMainActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }


}