package com.han.my_friend_kim_jung_san.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.han.my_friend_kim_jung_san.ui.home.HomeFragment
import com.han.my_friend_kim_jung_san.R
import com.han.my_friend_kim_jung_san.ui.account.AccountFragment
import com.han.my_friend_kim_jung_san.databinding.ActivityMainBinding
import com.han.my_friend_kim_jung_san.ui.calculation.OperationsFragment
import com.han.my_friend_kim_jung_san.ui.meetingroom.MeetingroomListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNav()
    }

    private fun initNav(){
        binding.bottomNav.background = null
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, HomeFragment())
            .commitAllowingStateLoss()

        binding.floatingButton.setOnClickListener {
            Toast.makeText(applicationContext,"home", Toast.LENGTH_SHORT).show()
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.home -> {
                    binding.floatingButton.setOnClickListener {
                        Toast.makeText(applicationContext,"home", Toast.LENGTH_SHORT).show()
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.operations -> {
                    binding.floatingButton.setOnClickListener {
                        Toast.makeText(applicationContext,"oper", Toast.LENGTH_SHORT).show()
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, OperationsFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.account -> {
                    binding.floatingButton.setOnClickListener {
                        Toast.makeText(applicationContext,"account", Toast.LENGTH_SHORT).show()
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, AccountFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.chat -> {
                    binding.floatingButton.setOnClickListener {
                        Toast.makeText(applicationContext, "myMeetingList", Toast.LENGTH_SHORT).show()
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, MeetingroomListFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}