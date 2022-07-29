package com.han.my_friend_kim_jung_san.operation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.han.my_friend_kim_jung_san.databinding.FragmentOperationsBinding


class OperationsFragment : Fragment() {
    lateinit var binding: FragmentOperationsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOperationsBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init(){
        binding.cardView1.setOnClickListener {
            val intent = Intent(context, FirstCalculationActivity::class.java)
            startActivity(intent)
        }
        binding.cardView2.setOnClickListener {
            val intent = Intent(context, SecondCalculationActivity::class.java)
            startActivity(intent)
        }
    }
}