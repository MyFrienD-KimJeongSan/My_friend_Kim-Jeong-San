package com.han.my_friend_kim_jung_san

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.han.my_friend_kim_jung_san.databinding.ActivityMainBinding
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainToHomeBtn.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()

            setSupportActionBar(binding.toolbar)
            getSupportActionBar()?.setDisplayShowCustomEnabled(true)
            getSupportActionBar()?.setDisplayShowTitleEnabled(false) //기본 제목을 없애줍니다.
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        }
    }
}