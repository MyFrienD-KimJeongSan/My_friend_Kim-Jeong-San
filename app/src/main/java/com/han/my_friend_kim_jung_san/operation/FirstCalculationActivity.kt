package com.han.my_friend_kim_jung_san.operation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.han.my_friend_kim_jung_san.R
import com.han.my_friend_kim_jung_san.databinding.ActivityFirstCalculationBinding

class FirstCalculationActivity : AppCompatActivity() {
    lateinit var binding: ActivityFirstCalculationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstCalculationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}