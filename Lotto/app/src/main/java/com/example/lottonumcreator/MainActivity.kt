package com.example.lottonumcreator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val clearButton:Button by lazy{
        findViewById<Button>(R.id.btn_clear)
    }
    private  val addButton:Button by lazy {
        findViewById<Button>(R.id.btn_add)
    }
    private val runButton:Button by lazy{
        findViewById<Button>(R.id.btn_run)
    }
    private val numberPicker:NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numPicker)
    }
    private var didRun = false

    private val pickNumberSet = hashSetOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
    }
    private fun initRunButton(){
        runButton.setOnClickListener{
            val list = getRandomNumber()
            Log.d("MainActivity",list.toString())
        }
    }
    private fun getRandomNumber() :List<Int>{
        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45) {
                this.add(i)
            }
        }
        numberList.shuffle()
        val newList = numberList.subList(0,6) //from to 0,1,2,3,4,5
        return newList.sorted()
    }

    private fun initAddButton(){
        addButton.setOnClickListener{
            if (didRun){
                Toast.makeText(this,"초기화 후에 시도해주세요.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pickNumberSet.size>=5){
                Toast.makeText(this,"번호는 5개까지만 선택할수 있습니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            if(pickNumberSet.contains(numberPicker.value)){
                Toast.makeText(this,"이미 선택한 번호입니다",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } //여기까지 예외처리


        }
    }
}