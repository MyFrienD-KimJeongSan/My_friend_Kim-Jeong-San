package com.han.my_friend_kim_jung_san.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.han.my_friend_kim_jung_san.utils.Inflate

abstract class BaseFragment<VB: ViewBinding>(
    private val inflate: Inflate<VB>) : Fragment()
{
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initAfterBinding()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    protected abstract fun initAfterBinding()
    fun showToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    fun startNextActivity(activity: Class<*>?) {
        val intent = Intent(context, activity)
        startActivity(intent)
    }
}