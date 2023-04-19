package com.creativedrewy.artwallpapers.webview

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.webkit.*

@SuppressLint("SetJavaScriptEnabled")
class RendererWebView(
    context: Context
) : WebView(context) {

    private val webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean = false

        override fun onPageFinished(view: WebView?, url: String?) {
            //TODO: Figure out actual init method here
            view?.loadUrl("javascript:runTest()")
            view?.loadUrl("javascript:resumeSketch()")

            isLoaded = true
        }
    }

    private val webChromeClient = object : WebChromeClient() {
        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            consoleMessage?.let { msg ->
                Log.d("ArtWallpaper", "${ msg.message() } :: From line ${ msg.lineNumber() } of ${ msg.sourceId() }")
            }

            return super.onConsoleMessage(consoleMessage)
        }
    }

    var isLoaded = false

    init {
        setWebContentsDebuggingEnabled(true)

        settings.userAgentString = "Android"
        settings.useWideViewPort = false
        settings.javaScriptEnabled = true
        settings.blockNetworkImage = false
        settings.blockNetworkLoads = true

        setWebViewClient(webViewClient)
        setWebChromeClient(webChromeClient)
    }
}