package com.creativedrewy.artwallpapers.service

import android.app.Presentation
import android.content.Context
import android.hardware.display.DisplayManager
import android.service.wallpaper.WallpaperService
import android.util.DisplayMetrics
import android.view.SurfaceHolder
import android.view.ViewGroup
import com.creativedrewy.artwallpapers.webview.RendererWebView

class ArtWallpaperService : WallpaperService() {

    override fun onCreateEngine(): Engine {
        return P5Engine(this)
    }

    inner class P5Engine(
        private val context: Context
    ): WallpaperService.Engine() {

        private var rendererWebView: RendererWebView? = null

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)

            val flags = DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY
            val density = DisplayMetrics.DENSITY_DEFAULT

            val displayManager = getSystemService(DISPLAY_SERVICE) as DisplayManager
            val virtualDisplay = displayManager.createVirtualDisplay("RenderDisp", width, height, density, holder.surface, flags)

            val presentation = Presentation(context, virtualDisplay.display)
            presentation.show()

            rendererWebView = RendererWebView(presentation.context)
            rendererWebView?.let {
                val params = ViewGroup.LayoutParams(width, height)
                presentation.setContentView(it, params)
            }
        }



        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)

            if (visible) {
                rendererWebView?.loadUrl("javascript:resumeSketch()")
            } else {
                rendererWebView?.loadUrl("javascript:pauseSketch()")
            }
        }

        override fun onDestroy() {
            rendererWebView?.loadUrl("")
            rendererWebView = null

            super.onDestroy()
        }

//        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
//            super.onSurfaceDestroyed(holder)
//        }

//        override fun onCreate(surfaceHolder: SurfaceHolder) {
//            super.onCreate(surfaceHolder)
//        }

//        override fun onSurfaceCreated(holder: SurfaceHolder) {
//            super.onSurfaceCreated(holder)
//        }

    }
}