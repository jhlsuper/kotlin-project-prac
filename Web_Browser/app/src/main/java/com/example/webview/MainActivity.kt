package com.example.webview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    private val webView: WebView by lazy {
        findViewById(R.id.webView)
    }
    private val addressBar: EditText by lazy {
        findViewById(R.id.addressBar)
    }
    private val goHomeButton: ImageButton by lazy {
        findViewById(R.id.btn_home)
    }
    private val goBackButton: ImageButton by lazy {
        findViewById(R.id.btn_back)
    }
    private val goForwardButton: ImageButton by lazy {
        findViewById(R.id.btn_forward)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        bindViews()
    }

    override fun onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack()
        }
        super.onBackPressed()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() {
        webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(DEFAULT_URL)
        }

    }

    private fun bindViews() {
        addressBar.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                webView.loadUrl(v.text.toString())
            }
            return@setOnEditorActionListener false
        }
        goBackButton.setOnClickListener {
            webView.goBack()
        }

        goForwardButton.setOnClickListener {
            webView.goForward()
        }
        goHomeButton.setOnClickListener {
            webView.loadUrl(DEFAULT_URL)
        }

    }
    companion object{
        private const val DEFAULT_URL ="http://www.google.com"
    }
}