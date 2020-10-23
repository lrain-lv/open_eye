package com.app.eye.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.eye.R
import com.app.eye.base.BaseActivity
import com.app.eye.http.RetrofitManager
import com.app.eye.rx.SchedulerUtils
import com.blankj.utilcode.util.CacheDiskUtils
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {

    override fun getLayoutRes(): Int = R.layout.activity_search

    override fun initView() {
        immersionBar.statusBarDarkFont(true).init()
    }

    override fun initData() {

    }

    override fun reConnect() {

    }

    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
        overridePendingTransition(0,R.anim.out_from_top)
    }

}