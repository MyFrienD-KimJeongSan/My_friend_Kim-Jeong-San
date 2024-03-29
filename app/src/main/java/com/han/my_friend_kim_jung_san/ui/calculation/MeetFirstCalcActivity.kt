package com.han.my_friend_kim_jung_san.ui.calculation


import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import com.han.my_friend_kim_jung_san.R
import com.han.my_friend_kim_jung_san.data.entity.Payment
import com.han.my_friend_kim_jung_san.data.remote.payment.PaymentService
import com.han.my_friend_kim_jung_san.databinding.ActivityMeetFirstCalcBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity
import java.text.DecimalFormat

class MeetFirstCalcActivity: BaseActivity<ActivityMeetFirstCalcBinding>(ActivityMeetFirstCalcBinding::inflate),CreatePaymentView {
    private var isOperation = false
    private var hasOperation = false
    private var costFocus = true
    private var memberFocus = false
    private var curPlus = false
    private var curMinus = false
    private var curMulti = false
    private var curDivide = false
    private var flag = false
    private var tmpResult = 0.0
    private val df = DecimalFormat("0")

    private var startDate = ""
    private var roomId = 0
    private var id = 1
    private var userIdList = arrayListOf<String>()
    private var userNameList = arrayListOf<String>()

    override fun initAfterBinding() {
        roomId = this.intent.getIntExtra("roomId", 0)
        startDate = this.intent.getStringExtra("startDate")!!
        userIdList = this.intent.getStringArrayListExtra("userIdList")!!
        userNameList = this.intent.getStringArrayListExtra("userNameList")!!
        id = this.intent.getIntExtra("id", 1)
        binding.backArrowIBtn.setOnClickListener{
            finish()
        }
        binding.memberInputTextView.text = userIdList.size.toString()

        val user = mutableMapOf<String, String>()
        userNameList.forEachIndexed { index, s ->
            user[userNameList[index]] = userIdList[index]
        }

        binding.finishIBtn.setOnClickListener {
            //payer 이름 받아와서 나중에 수정해야함
            if (binding.costInputTextView.text.isNotEmpty()){
                createPayment(Payment(binding.resultInputTextView.text.toString().toInt(),startDate,userIdList, userIdList[0] ,roomId))
            }
        }
    }



    fun buttonClicked(v: View){
        when(v.id){
            R.id.zeroButton -> numberClicked("0")
            R.id.oneButton -> numberClicked("1")
            R.id.twoButton -> numberClicked("2")
            R.id.threeButton -> numberClicked("3")
            R.id.fourButton -> numberClicked("4")
            R.id.fiveButton -> numberClicked("5")
            R.id.sixButton -> numberClicked("6")
            R.id.sevenButton -> numberClicked("7")
            R.id.eightButton -> numberClicked("8")
            R.id.nineButton -> numberClicked("9")
            R.id.plusButton -> operatorClicked("+")
            R.id.minusButton -> operatorClicked("-")
            R.id.multiButton -> operatorClicked("*")
            R.id.divideButton -> operatorClicked("/")
            R.id.acButton -> clearButton()
            R.id.equalButton -> resultButtonClicked()
            R.id.plusMinusButton -> plusMinusChange()
            R.id.dotButton -> dotClicked()
        }
    }
    private fun clearButton(){
        binding.costInputTextView.text = ""
        binding.resultInputTextView.text = ""
        costFocus = true
        isOperation = false
        hasOperation = false
        flag = false
        curPlus = false
        curMinus = false
        curMulti = false
        curDivide = false
        tmpResult = 0.0
        operatorButtonBackground()
    }
    @SuppressLint("SetTextI18n")
    private fun numberClicked(number: String){
        isOperation = false
        when {
            binding.costInputTextView.length() >= 15 || binding.memberInputTextView.length() >= 15 -> {
                showToast("15자리 까지만 입력할 수 있습니다.")
                return
            }
            costFocus && binding.costInputTextView.text.isEmpty() && number == "0" && !flag -> {
                showToast("0은 제일 앞에 올 수 없습니다.")
                return
            }
            memberFocus && binding.memberInputTextView.text.isEmpty() && number == "0" && !flag -> {
                showToast("0은 제일 앞에 올 수 없습니다.")
                return
            }
            costFocus -> {
                if(hasOperation && binding.costInputTextView.text.isNotEmpty() && !flag){
                    tmpResult = binding.costInputTextView.text.toString().toDouble()
                    binding.costInputTextView.text = ""
                    flag = true
                }
                binding.costInputTextView.append(number)
                if(binding.memberInputTextView.text.isNotEmpty()){
                    binding.resultInputTextView.text = df.format((binding.costInputTextView.text.toString().toDouble() / binding.memberInputTextView.text.toString().toDouble())).toString()
                }

            }
        }
    }
    private fun plusMinusChange(){
        when{
            costFocus -> {
                if(binding.costInputTextView.text.isEmpty()) return
                binding.costInputTextView.text = df.format((binding.costInputTextView.text.toString().toDouble() * -1)).toString()
            }
        }
    }
    private fun dotClicked(){
        when{
            costFocus -> {
                if(binding.costInputTextView.text.isEmpty()){
                    showToast(".이 맨 앞에 올 수 없습니다.")
                    return
                }
                if(!binding.costInputTextView.text.contains(".")){
                    binding.costInputTextView.append(".")
                    return
                }else{
                    showToast(".이 여러개 올 수 없습니다.")
                    return
                }
            }
        }
    }
    private fun operatorClicked(operator: String){
        if (costFocus && binding.costInputTextView.text.isEmpty()){
            showToast("연산자가 앞에 올 수 없습니다.")
            return
        }

        setCurrentOperator(operator)
        when{
            isOperation -> {
                setCurrentOperator(operator)
                operatorButtonBackground()
            }
            hasOperation -> {
                showToast("연산자는 한 번만 사용할 수 있습니다.")
                return
            }
        }
        operatorButtonBackground()
        isOperation = true
        hasOperation = true
    }

    private fun operatorButtonBackground(){
        if (curPlus){
            binding.plusButton.setBackgroundResource(R.drawable.clicked_plus)
        }else if(!curPlus){
            binding.plusButton.setBackgroundResource(R.drawable.plus)
        }
        if(curMinus){
            binding.minusButton.setBackgroundResource(R.drawable.clicked_minus)
        }else if(!curMinus){
            binding.minusButton.setBackgroundResource(R.drawable.minus)
        }
        if(curMulti){
            binding.multiButton.setBackgroundResource(R.drawable.clicked_multi)
        }else if(!curMulti){
            binding.multiButton.setBackgroundResource(R.drawable.multi)
        }
        if(curDivide){
            binding.divideButton.setBackgroundResource(R.drawable.clicked_divide)
        }else if(!curDivide){
            binding.divideButton.setBackgroundResource(R.drawable.divide)
        }
    }

    private fun setCurrentOperator(operator: String){
        when(operator){
            "+" -> {
                curPlus = true
                curMinus = false
                curMulti = false
                curDivide = false
            }
            "-" -> {
                curPlus = false
                curMinus = true
                curMulti = false
                curDivide = false
            }
            "*" -> {
                curPlus = false
                curMinus = false
                curMulti = true
                curDivide = false
            }
            "/" -> {
                curPlus = false
                curMinus = false
                curMulti = false
                curDivide = true
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun resultButtonClicked(){
        if (!curPlus && !curMinus && !curMulti && !curDivide){
            showToast("선택된 연산자가 없습니다.")
            return
        }
        when{
            curPlus -> {
                if(costFocus){
                    binding.costInputTextView.text = df.format((tmpResult + binding.costInputTextView.text.toString().toDouble())).toString()
                    if(binding.memberInputTextView.text.isNotEmpty()){
                        binding.resultInputTextView.text = df.format((binding.costInputTextView.text.toString().toDouble() / binding.memberInputTextView.text.toString().toDouble())).toString()
                    }
                }
                curPlus = false
            }
            curMinus -> {
                if(costFocus){
                    binding.costInputTextView.text = df.format((tmpResult - binding.costInputTextView.text.toString().toDouble())).toString()
                    if(binding.memberInputTextView.text.isNotEmpty()){
                        binding.resultInputTextView.text = df.format((binding.costInputTextView.text.toString().toDouble() / binding.memberInputTextView.text.toString().toDouble())).toString()
                    }
                }
                curMinus = false
            }
            curMulti -> {
                if(costFocus){
                    binding.costInputTextView.text = df.format((tmpResult * binding.costInputTextView.text.toString().toDouble())).toString()
                    if(binding.memberInputTextView.text.isNotEmpty()){
                        binding.resultInputTextView.text = df.format((binding.costInputTextView.text.toString().toDouble() / binding.memberInputTextView.text.toString().toDouble())).toString()
                    }
                }
                curMulti = false
            }
            curDivide -> {
                if(costFocus){
                    binding.costInputTextView.text = df.format((tmpResult / binding.costInputTextView.text.toString().toDouble())).toString()
                    if(binding.memberInputTextView.text.isNotEmpty()){
                        binding.resultInputTextView.text = df.format((binding.costInputTextView.text.toString().toDouble() / binding.memberInputTextView.text.toString().toDouble())).toString()
                    }
                }
                curDivide = false
            }
        }
        tmpResult = 0.0
        operatorButtonBackground()
        isOperation = false
        hasOperation = false
        flag = false
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