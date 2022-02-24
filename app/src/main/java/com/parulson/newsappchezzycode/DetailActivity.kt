package com.parulson.newsappchezzycode

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity


class DetailActivity : AppCompatActivity() {

    val wbDetail: WebView
    get() = findViewById(R.id.wbDetail)
    val pbDetail: ProgressBar
    get() = findViewById(R.id.pbDetail)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val url = intent.getStringExtra("URL")

        if (url != null){
            wbDetail.settings.javaScriptEnabled = true

//            val userAgent = String.format(
//                "%s [%s/%s]",
//                wbDetail.settings.userAgentString,
//                "App Android",
//                BuildConfig.VERSION_NAME
//            )
//            wbDetail.settings.userAgentString = userAgent
            //wbDetail.settings.userAgentString = "Mozilla/5.0 (Linux; U; Android 3.0; en-us; Xoom Build/HRI39) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13"
            wbDetail.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    pbDetail.visibility = View.GONE
                    wbDetail.visibility = View.VISIBLE
                }
            }
            wbDetail.loadUrl(url)
        }
    }
}