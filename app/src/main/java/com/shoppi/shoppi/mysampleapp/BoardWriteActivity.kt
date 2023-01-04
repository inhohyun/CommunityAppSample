package com.shoppi.shoppi.mysampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BoardWriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_write)

        val writeBtn = findViewById<Button>(R.id.writenUploadBtn)
        writeBtn.setOnClickListener {
            val writeText = findViewById<EditText>(R.id.writenTextArea)
// Write a message to the database

            val database = Firebase.database
            val myRef = database.getReference("board")
            //push 없으면 적은 값이 누적되는게 아닌 수정됨
            myRef.push().setValue(
                //model을 이용해 데이터를 저장함
               Model(writeText.text.toString())
            )
        }
    }
}