package com.han.my_friend_kim_jung_san.ui.meeting

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.han.my_friend_kim_jung_san.R
import com.han.my_friend_kim_jung_san.data.entity.Data
import com.han.my_friend_kim_jung_san.data.remote.room.RoomService
import com.han.my_friend_kim_jung_san.databinding.FragmentMeetingRoomBinding
import com.han.my_friend_kim_jung_san.databinding.MeetingItemBinding
import com.han.my_friend_kim_jung_san.ui.BaseFragment
import com.han.my_friend_kim_jung_san.ui.chat.ChatActivity
import com.han.my_friend_kim_jung_san.ui.home.AllSearchView
import com.kakao.sdk.user.UserApiClient

class MeetingRoomRVAdapter(val onClick: (Data) -> Unit): RecyclerView.Adapter<MeetingRoomRVAdapter.ViewHolder>() {
    val meetingList = mutableListOf<Data>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MeetingRoomRVAdapter.ViewHolder {
        val binding: MeetingItemBinding = MeetingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeetingRoomRVAdapter.ViewHolder, position: Int) {
        holder.bind(meetingList[position])
    }
    inner class ViewHolder(val binding: MeetingItemBinding): RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener {
                onClick(meetingList[bindingAdapterPosition])
            }
        }
        @SuppressLint("SetTextI18n")
        fun bind(data: Data){
            when(data.color){
                "red" -> binding.meetCategoryIV.setImageResource(R.drawable.red)
                "orange" -> binding.meetCategoryIV.setImageResource(R.drawable.orange)
                "yellow" -> binding.meetCategoryIV.setImageResource(R.drawable.yellow)
                "green" -> binding.meetCategoryIV.setImageResource(R.drawable.green)
                "blue" -> binding.meetCategoryIV.setImageResource(R.drawable.blue)
                "purple" -> binding.meetCategoryIV.setImageResource(R.drawable.purple)
            }
            binding.meetingTimeTV.text = "${data.startDate} ${data.startTime}"
            binding.meetingTitleTV.text = data.name

            var member = ""
            data.userList?.forEach {
                member += "${it.name},"
            }
            member = member.dropLast(1)
            binding.meetingMemberTV.text = member
        }
    }
    override fun getItemCount(): Int = meetingList.size


}
class MeetingRoomFragment : Fragment(), AllSearchView {
    private val meetingRoomAdapter = MeetingRoomRVAdapter{
        val intent = Intent(activity, ChatActivity::class.java)
        intent.putExtra("roomId", it.roomId!!.toInt())
        intent.putExtra("name", it.name.toString())
        intent.putExtra("startDate", it.startDate.toString())

        val userIdList = arrayListOf<String>() //userid, name
        val userNameList = arrayListOf<String>()

        it.userList.orEmpty().forEach { user ->
            userIdList.add(user.userId.toString())
            userNameList.add(user.name.toString())
        }

        intent.putExtra("userIdList", userIdList)
        intent.putExtra("userNameList", userNameList)
        startActivity(intent)
    }
    private lateinit var binding: FragmentMeetingRoomBinding
    private val meetingRoom = mutableListOf<Data>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMeetingRoomBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMeetingRoomBinding.bind(view)
        binding.myMeetingList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = meetingRoomAdapter
        }
        allSearch()
    }

    private fun allSearch(){
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("user", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i("user", "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                RoomService.allSearchRoom(this, user.id.toString())
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateAdapter(){
        meetingRoomAdapter.apply {
            meetingList.clear()
            meetingList.addAll(meetingRoom)
            notifyDataSetChanged()
        }
    }
    override fun onAllSearchSuccess(data: List<Data>?) {
        data?.forEach { list ->
            meetingRoom.add(list)
            updateAdapter()
        }
    }

    override fun onAllSearchFailure(code: Int?, message: String?) {
    }

}