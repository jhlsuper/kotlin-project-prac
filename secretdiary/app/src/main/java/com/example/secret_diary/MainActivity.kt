package com.example.secret_diary

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private var changePasswordMode = false

    private val numberPicker1 :NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1)
            .apply{
                minValue = 0
                maxValue =9
            }
    }
    private val numberPicker2 :NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2)
            .apply{
                minValue = 0
                maxValue =9
            }
    }
    private val numberPicker3 :NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3)
            .apply{
                minValue = 0
                maxValue =9
            }
    }
    private  val openButton : AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btn_open)
    }
    private val changePasswordButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btn_change)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3

        openButton.setOnClickListener{
            if(changePasswordMode){
                Toast.makeText(this,"비밀번호 변경중입니다",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordPreferences= getSharedPreferences("password", Context.MODE_PRIVATE)

            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
            if (passwordPreferences.getString("password","000").equals(passwordFromUser)){
                //패스워드 성공

                startActivity(Intent(this,DiaryActivity::class.java))
            }else{
                //실패패
            showErrorAlertDialog()
        }
            changePasswordButton.setOnClickListener{
                val passwordPreferences = getSharedPreferences("password",Context.MODE_PRIVATE)
                val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
                if (changePasswordMode){

                    passwordPreferences.edit(true) {
                        putString("password",passwordFromUser)

                    }
                    changePasswordMode = false
                    changePasswordButton.setBackgroundColor(Color.BLACK)
                    //번호를 저장하는 기능
                }else{
                    //changePasswordMode를 활성화 비밀번호가 맞는지를 체크크

                    if (passwordPreferences.getString("password","000").equals(passwordFromUser)){
                        changePasswordMode = true
                        Toast.makeText(this,"변경할 패스워드를 입력해주세요",Toast.LENGTH_SHORT).show()
                        changePasswordButton.setBackgroundColor(Color.RED)
//                startActivity()
                    }else{
                        //실패패
                        showErrorAlertDialog()
                    }
                }
            }

       }
    }
    private fun showErrorAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("실패!!")
            .setMessage("비밀번호가 잘못되었습니다.")
            .setPositiveButton("확인"){_,_-> }
            .create()
            .show()
    }

}