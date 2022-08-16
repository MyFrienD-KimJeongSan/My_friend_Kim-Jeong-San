package com.han.my_friend_kim_jung_san.ui.login

import android.content.Intent
import android.util.Log
import android.view.animation.AnimationUtils
import com.han.my_friend_kim_jung_san.ApplicationClass.Companion.TAG
import com.han.my_friend_kim_jung_san.R
import com.han.my_friend_kim_jung_san.databinding.ActivityLoginBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity
import com.han.my_friend_kim_jung_san.ui.main.MainActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient


class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    override fun initAfterBinding() {
        initAnim()
        binding.kakaoLoginBtn.setOnClickListener {
            login()
        }
        binding.mainPhotoIV.setOnClickListener {
            startActivityWithClear(MainActivity::class.java)
        }

    }
    private fun initAnim(){
        val titleAnim = AnimationUtils.loadAnimation(this, R.anim.title_anim)
        val subAnim = AnimationUtils.loadAnimation(this, R.anim.sub_title_anim)
        val lineAnim = AnimationUtils.loadAnimation(this, R.anim.line_anim)
        binding.splashTitleTV.startAnimation(titleAnim)
        binding.splashSubTV.startAnimation(subAnim)
        binding.line.startAnimation(lineAnim)
    }
    private fun login(){
//        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
//            if(error != null){
//                showToast("토큰 정보 보기 실패")
//            }else if(tokenInfo != null){
//                showToast("토큰 정보 보기 성공")
//                startActivityWithClear(MainActivity::class.java)
//            }
//        }
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        showToast("접근이 거부 됨(동의 취소)")
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        showToast("유효하지 않은 앱")
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        showToast("인증 수단이 유효하지 않아 인증할 수 없는 상태")
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        showToast("요청 파라미터 오류")
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        showToast("유효하지 않은 scope ID")
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        showToast("설정이 올바르지 않음(android key hash)")
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        showToast("서버 내부 에러")
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        showToast("앱이 요청 권한이 없음")
                    }
                    else -> { // Unknown
                        showToast("기타 에러")
                    }
                }
            }
            else if (token != null) {
                showToast("로그인에 성공하였습니다.")
                startActivityWithClear(MainActivity::class.java)
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    showToast("카카오톡으로 로그인 실패")

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    showToast("카카오톡으로 로그인 성공")
                    startActivityWithClear(MainActivity::class.java)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }

    }

}