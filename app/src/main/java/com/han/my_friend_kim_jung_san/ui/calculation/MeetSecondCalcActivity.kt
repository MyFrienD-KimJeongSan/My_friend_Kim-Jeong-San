package com.han.my_friend_kim_jung_san.ui.calculation

import android.R
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import com.han.my_friend_kim_jung_san.data.entity.Payment
import com.han.my_friend_kim_jung_san.data.remote.payment.PaymentService
import com.han.my_friend_kim_jung_san.databinding.ActivityMeetSecondCalcBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity

class MeetSecondCalcActivity : BaseActivity<ActivityMeetSecondCalcBinding>(ActivityMeetSecondCalcBinding::inflate), CreatePaymentView {

    override fun initAfterBinding() {

        //chatActivity에서 넘긴 값들
        val roomId = this.intent.getIntExtra("roomId", 0)
        val startDate = this.intent.getStringExtra("startDate")
        val userIdList = this.intent.getStringArrayListExtra("userIdList")
        val userNameList = this.intent.getStringArrayListExtra("userNameList")

        Log.i("test", "$userIdList $userNameList")

        binding.dateTV.text = startDate.orEmpty().replace("-", ".")



        val payers = arrayOf("한관진", "이수민")
        val user = mutableMapOf<String, String>()
        userNameList!!.forEachIndexed { index, s ->
            user[userNameList[index]] = userIdList!![index]
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
        showToast("정산 생성 성공")
        finish()
    }
    override fun onPaymentCreateFailure() {
    }

}