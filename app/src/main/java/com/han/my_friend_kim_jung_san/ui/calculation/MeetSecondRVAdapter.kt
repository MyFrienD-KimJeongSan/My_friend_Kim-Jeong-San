package com.han.my_friend_kim_jung_san.ui.calculation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.han.my_friend_kim_jung_san.databinding.ScParticipantItemBinding

class MeetSecondRVAdapter(private val participantList: Array<String>): RecyclerView.Adapter<MeetSecondRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MeetSecondRVAdapter.ViewHolder {
        val binding: ScParticipantItemBinding = ScParticipantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeetSecondRVAdapter.ViewHolder, position: Int) {
        //선택했을 때 이벤트 추가해야함
        holder.bind(participantList[position])
    }

    override fun getItemCount(): Int = participantList.size

    //모임방 참여자 불러오기 API 이용으로 바꿔야함
    inner class ViewHolder(val binding: ScParticipantItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(name: String){
            binding.nameTextView.text =  name
        }
    }
}