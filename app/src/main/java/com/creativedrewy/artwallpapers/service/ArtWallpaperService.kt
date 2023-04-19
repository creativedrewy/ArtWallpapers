package com.creativedrewy.artwallpapers.service

import android.content.Context
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder

class ArtWallpaperService : WallpaperService() {

    override fun onCreateEngine(): Engine {
        return P5Engine(this)
    }

    inner class P5Engine(
        private val context: Context
    ): WallpaperService.Engine() {

        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
        }

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            super.onSurfaceDestroyed(holder)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
        }

        override fun onDestroy() {
            super.onDestroy()
        }

    }
}