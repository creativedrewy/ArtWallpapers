package com.creativedrewy.artwallpapers.webview

import android.content.Context
import android.webkit.WebView

class RendererWebView(
    context: Context
) : WebView(context) {

    init {
//            WebView.setWebContentsDebuggingEnabled(true);
//            WebSettings webSettings = myWebView.getSettings();
//            webSettings.setUserAgentString("Android");
//            webSettings.setUseWideViewPort(false);
//            webSettings.setJavaScriptEnabled(true);
//            webSettings.setBlockNetworkImage(false);

//            WebViewClient client = new WebViewClient() {
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                    return true;
//                }
//
//                @Override
//                public void onPageFinished(WebView view, String url) {
//                    myWebView.loadUrl("javascript:runTest()");
//                }
//            };

        //            myWebView.setWebChromeClient(new WebChromeClient() {
        //                @Override
        //                public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        //                    Log.d("MyApplication", consoleMessage.message() + " -- From line "
        //                            + consoleMessage.lineNumber() + " of "
        //                            + consoleMessage.sourceId());
        //                    return super.onConsoleMessage(consoleMessage);
        //                }
        //            });
    }

}