package com.han.my_friend_kim_jung_san.ui.calculation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.han.my_friend_kim_jung_san.data.entity.Pay
import com.han.my_friend_kim_jung_san.databinding.OperationItemBinding
import java.text.DecimalFormat

class OperationListAdapter(private val payList: List<Pay>): RecyclerView.Adapter<OperationListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OperationListAdapter.ViewHolder {
        val binding: OperationItemBinding = OperationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OperationListAdapter.ViewHolder, position: Int) {
        holder.bind(payList[position])
    }

    override fun getItemCount(): Int = payList.size

    inner class ViewHolder(val binding: OperationItemBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(pay: Pay){
            val df = DecimalFormat("#,###")
            binding.opTitleTV.text = "${pay.date} ${pay.num}차 정산"
            binding.costTV.text = df.format(pay.amount)
            binding.payerTV.text = pay.payerName
        }
    }
}