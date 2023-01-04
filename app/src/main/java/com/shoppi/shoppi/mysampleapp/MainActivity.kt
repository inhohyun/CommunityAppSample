package com.shoppi.shoppi.mysampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shoppi.shoppi.mysampleapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //현재 회원가입한 유저의 UID 값을 firebase에서 가져오기
        Toast.makeText(this, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT).show()


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val joinBtnClicked = findViewById<Button>(R.id.joinBtn)
        //editText 가져오기 첫번째 방법
//        val email = findViewById<EditText>(R.id.emailArea)
//        val pwd = findViewById<EditText>(R.id.pwdArea)

        //editText 가져오기 두번째 방법 : 데이터 바인딩
        val email = binding.emailArea
        val pwd = binding.pwdArea

        joinBtnClicked.setOnClickListener{
            auth.createUserWithEmailAndPassword(email.text.toString(), pwd.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                      //회원가입 성공
                      Toast.makeText(this, "ok",Toast.LENGTH_SHORT).show()
                    } else {
                       //회원가입 실패
                      Toast.makeText(this, "no",Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.logoutBtn.setOnClickListener{
            //버튼 클릭시 로그아웃(삭제가 아닌 로그아웃)
            auth.signOut()
            Toast.makeText(this, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT).show()

        }

        binding.loginBtn.setOnClickListener{
            val email = binding.emailArea
            val pwd = binding.pwdArea

            auth.signInWithEmailAndPassword(email.text.toString(), pwd.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "ok",Toast.LENGTH_SHORT).show()
                        Toast.makeText(this, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(this, "no",Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }
}