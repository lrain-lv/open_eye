package com.app.eye

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import com.app.eye.http.RetrofitManager
import com.app.eye.http.mvvm.ApiService
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.mvvm.factory.*
import com.app.eye.ui.mvvm.viewmodel.*
import com.app.eye.widgets.EyeLoadMoreView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.module.LoadMoreModuleConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import leakcanary.LeakCanary
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


class MainApp : Application() {

    companion object {
        @JvmStatic
        lateinit var mContext: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        startKoin {
            androidContext(this@MainApp)
            modules(appModule)
        }
    }

    private val appModule = listOf(
        module {
            single { RetrofitManager.sInstance.createService(ApiService::class.java) }
            single { ServiceHelper(get()) }
        },
        module {
            viewModel { BadgeViewModel(get()) }
            viewModel { BrandWallViewModel(get()) }
            viewModel { CategoryViewModel(get()) }
            viewModel { CommunityViewModel(get()) }
            viewModel { DailyViewModel(get()) }
            viewModel { FindViewModel(get()) }
            viewModel { GalleryViewModel(get()) }
            viewModel { LightTopViewModel(get()) }
            viewModel { LoginViewModel(get()) }
            viewModel { MessageViewModel(get()) }
            viewModel { PopularViewModel(get()) }
            viewModel { PushViewModel(get()) }
            viewModel { RankViewModel(get()) }
            viewModel { RecommendViewModel(get()) }
            viewModel { SearchViewModel(get()) }
            viewModel { TagVideoViewModel(get()) }
            viewModel { TopicDetailViewModel(get()) }
            viewModel { TopicViewModel(get()) }
            viewModel { VideoDetailViewModel(get()) }
        }
    )

    override fun onLowMemory() {
        super.onLowMemory()
        val imageLoader = Coil.imageLoader(AppHelper.mContext)
        imageLoader.memoryCache.clear()
        imageLoader.bitmapPool.clear()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        val imageLoader = Coil.imageLoader(AppHelper.mContext)
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            imageLoader.memoryCache.clear()
            imageLoader.bitmapPool.clear()
        }
    }


}