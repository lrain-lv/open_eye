package com.app.eye.ui.activity

import android.Manifest
import android.graphics.Typeface
import android.view.KeyEvent
import android.view.View
import android.view.ViewPropertyAnimator
import com.app.eye.R
import com.app.eye.base.BaseActivity
import com.app.eye.http.RetrofitManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.gyf.immersionbar.BarHide
import com.tbruyelle.rxpermissions3.RxPermissions
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    override fun getLayoutRes(): Int = R.layout.activity_splash

    private lateinit var animator: ViewPropertyAnimator

    val permissions = RxPermissions(this)

    private val splash: String by lazy { SPUtils.getInstance("eye").getString("splash", "") }

    private lateinit var subscribe: Disposable
    override fun initView() {
        immersionBar.hideBar(BarHide.FLAG_HIDE_BAR).init()
        permissions.request(
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
        ).subscribe {
            if (it) {
                if (splash.isEmpty()) {
                    val typeface = Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf")
                    tv_en.typeface = typeface
                    iv_splash.setImageResource(R.mipmap.icon_splash)
                    animator = iv_splash.animate()
                        .scaleX(1.2f)
                        .scaleY(1.2f)
                        .setDuration(2000)
                        .withEndAction() {
                            ActivityUtils.startActivity(MainActivity::class.java)
                            finish()
                        }

                    animator.start()
                } else {
                    iv_account.visibility = View.GONE
                    layout.visibility = View.GONE
                    Glide.with(this)
                        .load(splash)
                        .into(iv_splash)
                    animator = iv_splash.animate()
                        .alpha(0.8f)
                        .setDuration(2000)
                        .withEndAction() {
                            ActivityUtils.startActivity(MainActivity::class.java)
                            finish()
                        }

                    animator.start()
                }
                subscribe = RetrofitManager.service.getConfigs()
                    .subscribe {
                        SPUtils.getInstance("eye").put("splash", it.startPageAd.imageUrl)
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