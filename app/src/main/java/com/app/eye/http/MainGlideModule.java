//package com.app.eye.http;
//
//import android.content.Context;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.GlideBuilder;
//import com.bumptech.glide.Registry;
//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
//import com.bumptech.glide.load.DecodeFormat;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
//import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
//import com.bumptech.glide.load.engine.cache.LruResourceCache;
//import com.bumptech.glide.load.model.GlideUrl;
//import com.bumptech.glide.module.AppGlideModule;
//import com.bumptech.glide.request.RequestOptions;
//import com.orhanobut.logger.Logger;
//
//import java.io.File;
//import java.io.InputStream;
//
//@GlideModule
//public class MainGlideModule extends AppGlideModule {
//
//    @Override
//    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
//        try {
//            Logger.e("ddd");
//            // Apply options to the builder here.
//            RequestOptions options = new RequestOptions().format(DecodeFormat.PREFER_RGB_565)
//                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
//            builder.setDefaultRequestOptions(options);
//            int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
//            int memoryCacheSize = maxMemory / 8;//设置图片内存缓存占用八分之一
//            //设置内存缓存大小
//            builder.setMemoryCache(new LruResourceCache(memoryCacheSize));
//            builder.setBitmapPool(new LruBitmapPool(memoryCacheSize));
//            File cacheDir;//指定的是数据的缓存地址
//            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
//                cacheDir = context.getExternalCacheDir();
//            } else {
//                cacheDir = context.getCacheDir();
//            }
//            //设置磁盘缓存大小
//            int diskCacheSize = 1024 * 1024 * 500;//最多可以缓存多少字节的数据
//            builder.setDiskCache(new DiskLruCacheFactory(cacheDir.getPath(), "eyeGlide", diskCacheSize));
//            builder.setLogLevel(Log.ERROR);
//        } catch (Exception e) {
//        }
//    }
//
//    @Override
//    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
//        Logger.e("ddd");
//        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(RetrofitManager.getSInstance().getClient()));
//    }
//
//    @Override
//    public boolean isManifestParsingEnabled() {
//        return false;
//    }
//}
