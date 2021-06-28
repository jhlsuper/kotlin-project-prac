package com.example.digital_frame

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private val addPhotoButton: Button by lazy {
        findViewById<Button>(R.id.btn_addPhoto)
    }

    private val startPhotoFrameModeButton: Button by lazy {
        findViewById<Button>(R.id.btn_startFrameMode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAddPhotoButton()
        initStartPhotoFrameModeButton()
    }

    private fun initAddPhotoButton() {
        addPhotoButton.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    //권한이 잘 부여되었을때
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    showPermissionContextPopup()
                }
                else -> {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        1000
                    )

                }
            }
        }
    }

    private fun showPermissionContextPopup() {
        //권한 팝업을 실행하는 함수
        AlertDialog.Builder(this).setTitle("권한이 필요합니다")
            .setMessage("전자액자 앱에서 사진을 불러오기 위해 권한이 필요합니다")
            .setPositiveButton("동의하기", { _, _ ->
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1000
                )
            })
            .setNegativeButton("취소하기", { _, _ -> })
            .create()
            .show()
    }

    private fun initStartPhotoFrameModeButton() {

    }
}