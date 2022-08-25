package com.han.my_friend_kim_jung_san.ui.calculation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.han.my_friend_kim_jung_san.data.entity.Pay
import com.han.my_friend_kim_jung_san.data.remote.payment.PaymentService
import com.han.my_friend_kim_jung_san.databinding.ActivityOperationListBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity

class OperationListActivity : BaseActivity<ActivityOperationListBinding>(ActivityOperationListBinding::inflate), PaymentView{
    private var startDate = ""
    private var roomId = 0
    private var id = 1
    private var userIdList = arrayListOf<String>()
    private var userNameList = arrayListOf<String>()
    override fun initAfterBinding() {
        startDate = this.intent.getStringExtra("startDate")!!
        roomId = this.intent.getIntExtra("roomId", 0)
        userIdList = this.intent.getStringArrayListExtra("userIdList")!!
        userNameList = this.intent.getStringArrayListExtra("userNameList")!!
        id = this.intent.getIntExtra("id", 1)
        searchPaymentList(roomId, id)

        binding.addPaymentIBtn.setOnClickListener {
            if (id == 1){
                val intent = Intent(this, MeetFirstCalcActivity::class.java)
                intent.putExtra("roomId", roomId)
                intent.putExtra("startDate", startDate)
                intent.putExtra("userIdList", userIdList)
                intent.putExtra("userNameList", userNameList)
                intent.putExtra("id", id)
                startActivity(intent)
                finish()
            }else if(id == 2){
                val intent = Intent(this, MeetSecondCalcActivity::class.java)
                intent.putExtra("roomId", roomId)
                intent.putExtra("startDate", startDate)
                intent.putExtra("userIdList", userIdList)
                intent.putExtra("userNameList", userNameList)
                intent.putExtra("id", id)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun searchPaymentList(roomId: Int, id: Int){
        PaymentService.searchPaymentList(this, roomId, id)
    }
    override fun onPaymentSearchSuccess(list: List<Pay>, id: Int) {
        val operationListAdapter = OperationListAdapter(list)
        binding.operationRV.adapter = operationListAdapter
        binding.operationRV.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
    }

    override fun onPaymentSearchNullSuccess(id: Int) {
    }

    override fun onPaymentSearchFailure() {
    }
}