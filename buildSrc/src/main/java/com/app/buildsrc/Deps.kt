package com.app.buildsrc

import com.app.buildsrc.Versions.koin_version

object Versions {
    const val gradle = "4.1.0"

    const val applicationId = "com.app.eye"
    const val minSdkVersion = 21
    const val targetSdkVersion = 29
    const val compileSdkVersion = 29
    const val versionCode = 1
    const val versionName = "1.0"

    const val kotlin_version = "1.4.10"
    const val koin_version = "2.2.0"
}

object Deps {
    const val appcompat = "androidx.appcompat:appcompat:1.2.0"
    const val cardview = "androidx.cardview:cardview:1.0.0"
    const val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
    const val core_ktx = "androidx.core:core-ktx:1.3.2"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val rxjava3 = "io.reactivex.rxjava3:rxjava:3.0.7"
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:2.9.0"
    const val adapter_rxjava = "com.squareup.retrofit2:adapter-rxjava3:2.9.0"
    const val glide = "com.github.bumptech.glide:glide:4.11.0"
    const val glide_compiler = "com.github.bumptech.glide:compiler:4.11.0"
    const val glide_annotations = "com.github.bumptech.glide:annotations:4.11.0"
    const val glide_transformations = "jp.wasabeef:glide-transformations:4.3.0"
    const val adapterHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4"
    const val logger = "com.orhanobut:logger:2.2.0"
    const val utilCode = "com.blankj:utilcodex:1.29.0"
    const val immersionbar = "com.gyf.immersionbar:immersionbar:3.0.0"
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:3.4.1"
    const val okhttp3_integration = "com.github.bumptech.glide:okhttp3-integration:4.11.0"
    const val rxandroid = "io.reactivex.rxjava3:rxandroid:3.0.0"
    const val fragmentationx = "me.yokeyword:fragmentationx:1.0.2"
    const val eventbus = "org.greenrobot:eventbus:3.2.0"
    const val FlycoTabLayout = "com.flyco.tablayout:FlycoTabLayout_Lib:2.1.2@aar"
    const val banner = "com.youth.banner:banner:2.1.0"
    const val itemdecoration = "com.github.thekhaeng:recycler-margin:1.2.1"
    const val rxpermissions = "com.github.tbruyelle:rxpermissions:0.12"
    const val easypermissions = "pub.devrel:easypermissions:3.0.0"
    const val agentweb = "com.just.agentweb:agentweb-androidx:4.1.4"
    const val vlayout = "com.alibaba.android:vlayout:1.2.36@aar"
    const val material = "com.google.android.material:material:1.0.0"
    const val matisse = "com.zhihu.android:matisse:0.5.3-beta3"
    const val photoView = "com.github.chrisbanes:PhotoView:2.0.0"
    const val gpuimage = "jp.co.cyberagent.android:gpuimage:2.1.0"
    const val lottie = "com.airbnb.android:lottie:3.5.0"
    const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
    const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
    const val extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"
    const val koin_scope = "org.koin:koin-androidx-scope:${koin_version}"
    const val koin_viewmodel = "org.koin:koin-androidx-viewmodel:$koin_version"
    const val koin_ext = "org.koin:koin-androidx-ext:$koin_version"
    const val koin_fragment = "org.koin:koin-androidx-fragment:$koin_version"
    const val coil = "io.coil-kt:coil:1.1.0"
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.5"
    const val legacy = "androidx.legacy:legacy-support-v4:1.0.0"
    const val startUp = "androidx.startup:startup-runtime:1.0.0-alpha03"
}