package com.han.my_friend_kim_jung_san.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.han.my_friend_kim_jung_san.data.local.MenuData
import com.han.my_friend_kim_jung_san.databinding.ActivityChatBinding
import com.han.my_friend_kim_jung_san.databinding.ChatVoteItemBinding
import com.han.my_friend_kim_jung_san.databinding.VoteMenuItemBinding
import java.util.*

class VoteMenuRVAdapter(private val items: ArrayList<MenuData>) : RecyclerView.Adapter<VoteMenuRVAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoteMenuRVAdapter.ViewHolder {
        val binding: VoteMenuItemBinding = VoteMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: VoteMenuRVAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size


    inner class ViewHolder(val binding: VoteMenuItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(menuData: MenuData) {
            binding.voteBtn1.isChecked = menuData.isChecked
            binding.menuNameTV.text = menuData.name
            binding.menuPriceTV.text = menuData.price
            binding.menuQuanTV.text = menuData.quantity
            binding.menuTotalPriceTV.text = menuData.totalPrice
            binding.menuPeopleTV.text = menuData.people
        }

    }
}