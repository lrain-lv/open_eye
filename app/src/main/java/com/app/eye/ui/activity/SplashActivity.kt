package com.app.eye.ui.activity

import android.Manifest
import android.graphics.Typeface
import android.view.KeyEvent
import android.view.View
import android.view.ViewPropertyAnimator
import com.app.eye.R
import com.app.eye.base.BaseActivity
import com.app.eye.http.RetrofitManager
import com.app.eye.rx.SchedulerUtils
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.gyf.immersionbar.BarHide
import com.orhanobut.logger.Logger
import com.tbruyelle.rxpermissions3.RxPermissions
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {

    override fun getLayoutRes(): Int = R.layout.activity_splash

    private lateinit var animator: ViewPropertyAnimator

    val permissions = RxPermissions(this)

    private val splash: String by lazy { SPUtils.getInstance("eye").getString("splash", "") }

    private lateinit var subscribe: Disposable
    override fun initView() {
        immersionBar.hideBar(BarHide.FLAG_HIDE_BAR).init()
        subscribe = RetrofitManager.service.getConfigs()
            .subscribeOn(Schedulers.io())
            .subscribe {
                SPUtils.getInstance("eye").put("splash", it.startPageAd.imageUrl)
            }
        tv_skip.setOnClickListener {
            ActivityUtils.startActivity(MainActivity::class.java)
            finish()
        }
        permissions.request(
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
        ).subscribe {
            if (it) {
                Observable.intervalRange(0, 4, 0, 1, TimeUnit.SECONDS)
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

            } else {
                ToastUtils.showShort("开启所有权限才能继续使用~~")
                ActivityUtils.finishActivity(this)
            }
        }


    }

    override fun onDestroy() {
        animator.cancel()
        subscribe.dispose()
        super.onDestroy()

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean = false

    override fun initData() {
    }

    override fun reConnect() {
    }
}