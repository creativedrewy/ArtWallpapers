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
//Inspired by @aemkei https://twitter.com/aemkei/status/1378106731386040322
y=0
w=1024

function setup() {
	noStroke()
}

function draw() {
  y+=s=1
  copy(0,0,w,w,0,s,w,w)
  fill("white")
  rect(0,0,w,s)
  for(i=s;i--;)
    for(x=w;x--;){
      a=x^y+i;
      b=a^a*2;
      if(b%63==0){
        fill("red")
        rect(x,i,2,2)
      }
      if(b%65==0){
        fill("blue")
        rect(x,i,2,2)
      }
      if(b%66==0){
        fill("green")
        rect(x,i,2,2)
      }
			if(b%31==0){
        fill("black")
        rect(x,i,2,2)
      }
    }
}
    """.trimIndent()
}