package com.han.my_friend_kim_jung_san.ui.meeting.invite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.han.my_friend_kim_jung_san.data.entity.User
import com.han.my_friend_kim_jung_san.databinding.InviteItemBinding

class RoomMemberRVAdapter(private val userList: ArrayList<User>):RecyclerView.Adapter<RoomMemberRVAdapter.ViewHolder>() {

    //x 버튼 클릭시 초대된 유저에서 삭제
    interface UserRemoveClickListener{

        fun onRemoveUser(position: Int)
    }
    private lateinit var userItemClickListener: UserRemoveClickListener

    fun setItemClickListener(itemClickListener: UserRemoveClickListener){
        userItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoomMemberRVAdapter.ViewHolder {
        val binding: InviteItemBinding = InviteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomMemberRVAdapter.ViewHolder, position: Int) {
        holder.bind(userList[position])
        holder.binding.removeIV.setOnClickListener {
            userItemClickListener.onRemoveUser(position)
        }
    }

    override fun getItemCount(): Int = userList.size

    inner class ViewHolder(val binding: InviteItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.nameTV.text = user.name
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position: Int){
        userList.removeAt(position)
        notifyDataSetChanged()
    }
}