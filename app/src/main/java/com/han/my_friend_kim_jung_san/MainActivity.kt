package com.han.my_friend_kim_jung_san

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.han.my_friend_kim_jung_san.account.AccountFragment
import com.han.my_friend_kim_jung_san.databinding.ActivityMainBinding
import com.han.my_friend_kim_jung_san.operation.OperationsFragment

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
            .replace(R.id.frameLayout, OperationsFragment())
            .commitAllowingStateLoss()

        binding.bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId){
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
            }
            false
        }
    }
}