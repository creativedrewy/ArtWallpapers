package com.creativedrewy.artwallpapers;

import android.app.Presentation;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWallpaperService extends WallpaperService {
    // We can have multiple engines running at once (since you might have one on your home screen
    // and another in the settings panel, for instance), so for debugging it's useful to keep track
    // of which one is which. We give each an id based on this nextEngineId.
    static int nextEngineId = 1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public Engine onCreateEngine() {
        return new MyEngine(this);
    }

    public class MyEngine extends Engine {
        private Context myContext;
        private WebView myWebView;
        private SurfaceHolder myHolder;
        private int myId;

        public MyEngine(Context context) {
            myId = nextEngineId;
            nextEngineId++;
            myContext = context;
            myWebView = null;
            log("Engine created.");
        }

        private void log(String message) {
            Log.d("MyLWP " + myId, message);
        }

        private void logError(String message) {
            Log.e("MyLWP " + myId, message);
        }

        public void incomingMessage() {
            // The message from the WebView might not be on the right thread, so we use a broadcast to fix that.
            Intent intent = new Intent("draw-frame");
            //LocalBroadcastManager.getInstance(myContext).sendBroadcast(intent);
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            log("On Create");
            super.onCreate(surfaceHolder);
        }

        @Override
        public void onDestroy() {
            log("On Destroy");
            super.onDestroy();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            log("On Visibility Changed " + String.valueOf(visible));
            super.onVisibilityChanged(visible);

            if (myWebView == null) {
                return;
            }

            // To save battery, when we're not visible we want the WebView to stop processing,
            // so we use the loadUrl mechanism to call some JavaScript to tell it to pause.
            if (visible) {
                //myWebView.loadUrl("javascript:resumeWallpaper()");
                //drawFrame();
                //handler.post(drawRunner);
            } else {
                //myWebView.loadUrl("javascript:pauseWallpaper()");
                //handler.removeCallbacks(drawRunner);
            }
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            log("On Surface Create");
            super.onSurfaceCreated(holder);

            myHolder = holder;

            // Create WebView
            if (myWebView != null) {
                myWebView.destroy();
            }

//            WebViewClient client = new WebViewClient() {
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                    return false;
//                }
//            };

//            myWebView = new WebView(myContext);
//            myWebView.setWebViewClient(client);
//
//            WebView.setWebContentsDebuggingEnabled(true);
//            WebSettings webSettings = myWebView.getSettings();
//            webSettings.setJavaScriptEnabled(true);
//            webSettings.setDomStorageEnabled(true);
//            webSettings.setBlockNetworkLoads(false);
//            webSettings.setBlockNetworkImage(false);
//
//            myJSInterface = new JSInterface(this);
//            myWebView.addJavascriptInterface(myJSInterface, "androidWallpaperInterface");
//
//            myWebView.loadUrl("https://threejs.org/examples/webgl_loader_ldraw.html");
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            log("On Surface Destroy");

            if (myWebView != null) {
                myWebView.destroy();
                myWebView = null;
            }

            super.onSurfaceDestroyed(holder);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            log("On Surface Changed " + String.valueOf(format) + ", " + String.valueOf(width) + ", " + String.valueOf(height));
            super.onSurfaceChanged(holder, format, width, height);

            DisplayManager mDisplayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);

            int flags = DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY;
            int density = DisplayMetrics.DENSITY_DEFAULT;

            VirtualDisplay virtualDisplay = mDisplayManager.createVirtualDisplay("MyVirtualDisplay",
                    width, height, density, holder.getSurface(), flags);

            Presentation myPresentation = new Presentation(myContext, virtualDisplay.getDisplay());
            myPresentation.show();

            myWebView = new WebView(myPresentation.getContext());
            WebView.setWebContentsDebuggingEnabled(true);
            WebSettings webSettings = myWebView.getSettings();
            webSettings.setUserAgentString("Android");
            webSettings.setUseWideViewPort(false);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setBlockNetworkImage(false);

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams (width, height);
            myPresentation.setContentView(myWebView, params);

            WebViewClient client = new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    myWebView.loadUrl("javascript:runTest()");
                }
            };

            myWebView.setWebViewClient(client);
            myWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                    Log.d("MyApplication", consoleMessage.message() + " -- From line "
                            + consoleMessage.lineNumber() + " of "
                            + consoleMessage.sourceId());
                    return super.onConsoleMessage(consoleMessage);
                }
            });

            myWebView.loadUrl("file:///android_asset/bin/index.html");

//            if (myWebView != null) {
//                int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
//                int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
//                myWebView.measure(widthSpec, heightSpec);
//                myWebView.layout(0, 0, width, height);
//            }
        }
    }
}