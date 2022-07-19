package com.han.my_friend_kim_jung_san

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.han.my_friend_kim_jung_san.databinding.FragmentOperationsBinding


class OperationsFragment : Fragment() {
    lateinit var binding: FragmentOperationsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOperationsBinding.inflate(inflater, container, false)
        return binding.root
    }

}