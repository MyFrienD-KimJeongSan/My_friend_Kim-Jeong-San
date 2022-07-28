package com.han.my_friend_kim_jung_san.operation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.han.my_friend_kim_jung_san.R
import com.han.my_friend_kim_jung_san.databinding.ActivitySecondCalculationBinding

class SecondCalculationActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecondCalculationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondCalculationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}