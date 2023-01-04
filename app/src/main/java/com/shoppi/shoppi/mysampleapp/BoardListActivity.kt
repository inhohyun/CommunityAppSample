package com.shoppi.shoppi.mysampleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BoardListActivity : AppCompatActivity() {
    //lateinit : 타입은 정해주고 값은 다음에 넣어주겠다. : 모든 함수에서 사용하기위해 전역변수로 선언(값을 미리 넣어주기 힘듦)
    lateinit var LVAdapter: ListViewAdapter

    val list = mutableListOf<Model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_list)

        val writenBtn = findViewById<Button>(R.id.writenBtn)
        writenBtn.setOnClickListener{
            val intent = Intent(this, BoardWriteActivity::class.java)
            startActivity(intent)


        }

        //firebase에서 데이터 불러오기
        getData()

        //전역변수 list에서 받은 값을 ListViewAdapter로 보내줌
        //getData로 데이터를 가져오기 -> firebase : 비동기처리로 가져옴 = 어댑터로 보내는 작업과 동시진행
        //따라서 getData가 끝나기전에 아래 코드가 실행되어 저장된같을 정상적으로 할당할 수 없음
        //-> 정답 : getData 하단에 적어둠
        LVAdapter = ListViewAdapter(list)
        var lv = findViewById<ListView>(R.id.lv)
        lv.adapter = LVAdapter



    }

    fun getData(){
        val database = Firebase.database

        val myRef = database.getReference("board")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
           Log.d("BoardListActivity", dataSnapshot.toString())
                // ...
                //가져온 데이터를 반복문을 통해 하나씩 가져오기
                for(dataModel in dataSnapshot.children){

                    //만들어둔 Model의 형태로 데이터를 가져올 것
                    val item = dataModel.getValue(Model::class.java)
                    Log.d("BoardListActivity", item.toString())
                    list.add(item!!)
                }
                //데이터를 가져온 후 다시 어댑터를 동기화
                LVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("BoardListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        //board의 데이터를 가져오기
        myRef.addValueEventListener(postListener)
    }
}