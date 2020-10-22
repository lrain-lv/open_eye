package com.app.eye.ui.activity

import android.graphics.Typeface
import android.view.KeyEvent
import android.view.ViewPropertyAnimator
import com.app.eye.R
import com.app.eye.base.BaseActivity
import com.blankj.utilcode.util.ActivityUtils
import com.gyf.immersionbar.BarHide
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    override fun getLayoutRes(): Int = R.layout.activity_splash

    private lateinit var animator: ViewPropertyAnimator

    override fun initView() {
        immersionBar.hideBar(BarHide.FLAG_HIDE_BAR).init()
        var typeface = Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf")
        tv_en.typeface = typeface
        animator = iv_splash.animate()
            .scaleX(1.2f)
            .scaleY(1.2f)
            .alpha(0.8f)
            .setDuration(2000)
            .withEndAction() {
                ActivityUtils.startActivity(MainActivity::class.java)
                finish()
            }
        animator.start()

    }

    override fun onDestroy() {
        animator.cancel()
        super.onDestroy()

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean = false

    override fun initData() {
    }
}