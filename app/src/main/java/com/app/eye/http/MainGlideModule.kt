package com.app.eye.http

import android.content.Context
import android.os.Environment
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.io.InputStream

@GlideModule
class MainGlideModule : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean = false

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.replace(
            GlideUrl::class.java, InputStream::class.java,
            OkHttpUrlLoader.Factory(RetrofitManager.sInstance.client)
        )
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // Apply options to the builder here.

        // Apply options to the builder here.
        val options = RequestOptions().format(DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        builder.setDefaultRequestOptions(options)
        val maxMemory = Runtime.getRuntime().maxMemory().toInt() //获取系统分配给应用的总内存大小
        val memoryCacheSize = maxMemory / 8 //设置图片内存缓存占用八分之一
        //设置内存缓存大小
        //设置内存缓存大小
        builder.setMemoryCache(LruResourceCache(memoryCacheSize.toLong()))
        builder.setBitmapPool(LruBitmapPool(memoryCacheSize.toLong()))
        val cacheDir: File? =
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                context.externalCacheDir
            } else {
                context.cacheDir
            }//指定的是数据的缓存地址

        //设置磁盘缓存大小
        //设置磁盘缓存大小
        val diskCacheSize = 1024 * 1024 * 500 //最多可以缓存多少字节的数据

        builder.setDiskCache(
            DiskLruCacheFactory(
                cacheDir!!.path, "eyeGlide",
                diskCacheSize.toLong()
            )
        )
        builder.setLogLevel(Log.ERROR)
    }
}