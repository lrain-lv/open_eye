package com.app.eye.ui.activity

import android.Manifest.permission.*
import android.graphics.Typeface
import android.view.KeyEvent
import android.view.View
import android.view.ViewPropertyAnimator
import com.app.eye.R
import com.app.eye.base.BaseActivity
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.rx.isSuccess
import com.app.eye.rx.setOnClickListener
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.gyf.immersionbar.BarHide
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.*
import pub.devrel.easypermissions.EasyPermissions
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity(), EasyPermissions.PermissionCallbacks {

    override fun getLayoutRes(): Int = R.layout.activity_splash
    private val scope = MainScope()
    private lateinit var animator: ViewPropertyAnimator

    private val perms = arrayOf(
        ACCESS_NETWORK_STATE,
        WRITE_EXTERNAL_STORAGE,
        READ_PHONE_STATE
    )

    private val splash: String by lazy { SPUtils.getInstance("eye").getString("splash", "") }


    override fun initView() {
        immersionBar.hideBar(BarHide.FLAG_HIDE_BAR).init()
        scope.launch(Dispatchers.IO) {
            val result = ServiceHelper.getInstance().getConfigs()
            if (result.isSuccess()) {
                SPUtils.getInstance("eye")
                    .put("splash", (result as EyeResult.Success).data.startPageAd.imageUrl)
            }
        }
        setOnClickListener(tv_skip) {
            ActivityUtils.startActivity(MainActivity::class.java)
            finish()
        }
        if (EasyPermissions.hasPermissions(this, *perms)) {
            init()
        } else {
            EasyPermissions.requestPermissions(this, "运行APP需要申请响应的权限", 1000, *perms)
        }

    }

    private lateinit var subscribe: Disposable
    private fun init() {
        subscribe = Observable.intervalRange(0, 4, 0, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                tv_time.visibility = View.VISIBLE
                tv_skip.visibility = View.VISIBLE
                tv_time.text = "3秒"
                if (splash.isEmpty()) {
                    val typeface = Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf")
                    tv_en.typeface = typeface
                    iv_splash.setImageResource(R.mipmap.icon_splash)
                    animator = iv_splash.animate()
                        .scaleX(1.2f)
                        .scaleY(1.2f)
                        .setDuration(2500)
                    animator.start()
                } else {
                    iv_account.visibility = View.GONE
                    layout.visibility = View.GONE
                    Glide.with(this)
                        .load(splash)
                        .into(iv_splash)
                    animator = iv_splash.animate()
                        .alpha(0.85f)
                        .scaleX(1.1f)
                        .scaleY(1.1f)
                        .setDuration(2500)
                    animator.start()
                }
            }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                tv_time.text = "${3 - it} 秒"
                if (it.toInt() == 3) {
                    ActivityUtils.startActivity(MainActivity::class.java)
                    finish()
                }
            }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        ToastUtils.showShort("开启所有权限才能继续使用~~")
        ActivityUtils.finishActivity(this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        init()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onDestroy() {
        animator.cancel()
        if (scope.isActive) {
            scope.cancel()
        }
        if (!subscribe.isDisposed)
            subscribe.dispose()
        super.onDestroy()

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean = false

    override fun initData() {
    }

    override fun reConnect() {
    }
}