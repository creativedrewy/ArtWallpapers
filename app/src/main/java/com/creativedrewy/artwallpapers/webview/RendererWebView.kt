package com.creativedrewy.artwallpapers.webview

import android.annotation.SuppressLint
import android.content.Context
import android.util.Base64
import android.util.Log
import android.webkit.*
import java.nio.charset.Charset


@SuppressLint("SetJavaScriptEnabled")
class RendererWebView(
    context: Context
) : WebView(context) {

    private val webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean = false

        override fun onPageFinished(view: WebView?, url: String?) {
            //TODO: Figure out actual init method here
            val encodedSketch = Base64.encodeToString(testSketch.toByteArray(Charset.forName("UTF-8")), Base64.DEFAULT)

            view?.loadUrl("javascript:loadEncodedSketch('$encodedSketch', '400', '850')")
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

    var testSketch = """
        let rectX = 0;
        let fr = 30;
        let clr;
        
        function setup() {
          createCanvas(400, 400);
        
          background(200);
          clr = color(255, 0, 0);
        
          frameRate(30);
        }
        
        function draw() {
          background(200);
          rectX += 1; // Move Rectangle
        
          if (rectX >= width) {
            // If you go off screen.
            if (fr === 30) {
              clr = color(0, 0, 255);
              fr = 10;
              //frameRate(fr);
            } else {
              clr = color(255, 0, 0);
              fr = 30;
              //frameRate(fr);
            }
            rectX = 0;
          }
        
          fill(clr);
          rect(rectX, height / 2, 20, 20);
        }
    """.trimIndent()
}