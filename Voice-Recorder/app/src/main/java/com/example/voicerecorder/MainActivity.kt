package com.example.voicerecorder

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private val recordButton: RecordButton by lazy {
        findViewById(R.id.recordButton)
    }
    //필요한 권한 선언
    private val requiredPermissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    //초기 state 설정
    private var state = State.BEFORE_RECORDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestAudioPermission()
        initViews()
    }

    private fun requestAudioPermission(){
        requestPermissions(requiredPermissions, REQUEST_RECORD_AUDIO_PERMISSION)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //권한이 부여받은게 맞는지 check 권한부여받았으면 true 아니면 false
        val audioRequestPermissionGranted =
            requestCode == REQUEST_RECORD_AUDIO_PERMISSION &&
                grantResults.firstOrNull() ==PackageManager.PERMISSION_GRANTED

        //권한이 부여되지않으면 어플 종료
        if(!audioRequestPermissionGranted){
            finish()
        }
    }

    private fun initViews() {
        recordButton.updateIconWithState(state)
    }
    
    companion object{
        //permission code 선언
        private const val REQUEST_RECORD_AUDIO_PERMISSION =201
    }
}