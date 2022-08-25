package com.han.my_friend_kim_jung_san.ui.calculation

import android.R
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import com.han.my_friend_kim_jung_san.data.entity.Pay
import com.han.my_friend_kim_jung_san.data.entity.Payment
import com.han.my_friend_kim_jung_san.data.remote.payment.PaymentService
import com.han.my_friend_kim_jung_san.databinding.ActivityMeetSecondCalcBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity

class MeetSecondCalcActivity : BaseActivity<ActivityMeetSecondCalcBinding>(ActivityMeetSecondCalcBinding::inflate), CreatePaymentView {

    private var startDate = ""
    private var roomId = 0
    private var id = 2
    private var userIdList = arrayListOf<String>()
    private var userNameList = arrayListOf<String>()
    override fun initAfterBinding() {

        //chatActivity에서 넘긴 값들
        roomId = this.intent.getIntExtra("roomId", 0)
        startDate = this.intent.getStringExtra("startDate")!!
        userIdList = this.intent.getStringArrayListExtra("userIdList")!!
        userNameList = this.intent.getStringArrayListExtra("userNameList")!!
        id = this.intent.getIntExtra("id", 2)
        Log.i("test", "$userIdList $userNameList")

        binding.dateTV.text = startDate.orEmpty().replace("-", ".")



        val payers = arrayOf("한관진", "이수민")

        val user = mutableMapOf<String, String>()
        userNameList.forEachIndexed { index, s ->
            user[userNameList[index]] = userIdList[index]
        }

        val payerSpinner : Spinner = binding.payerSpinner
        val adapter : ArrayAdapter<String> = ArrayAdapter(this, R.layout.simple_list_item_1, payers)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        payerSpinner.adapter = adapter
        var curPayer = ""
        payerSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                curPayer = payerSpinner.getItemAtPosition(position).toString()
                Log.i("spinner", curPayer)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                curPayer = payers[0]
                Log.i("spinner", curPayer)
            }
        }


        //참여자 recyclerview adapter
        val meetSecondRVAdapter = MeetSecondRVAdapter(payers)
        binding.participantRV.adapter = meetSecondRVAdapter
        binding.participantRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        binding.finishIBtn.setOnClickListener {
            Log.i("amount", "${binding.costET.text} $startDate $userIdList ${user[curPayer]} $roomId")
            createPayment(Payment(binding.costET.text.toString().toInt(),startDate,userIdList, user[curPayer] ,roomId))
        }

    }


    private fun createPayment(payment: Payment){
        PaymentService.createPayment(this, payment)
    }

    override fun onPaymentCreateSuccess() {
        val intent = Intent(this, OperationListActivity::class.java)
        intent.putExtra("roomId", roomId)
        intent.putExtra("startDate", startDate)
        intent.putExtra("userIdList", userIdList)
        intent.putExtra("userNameList", userNameList)
        intent.putExtra("id", id)
        startActivity(intent)
        finish()
    }
    override fun onPaymentCreateFailure() {
    }



}