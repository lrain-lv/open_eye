package com.app.eye.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import com.app.eye.R
import com.app.eye.base.BaseActivity
import com.app.eye.rx.getAgentWeb
import com.blankj.utilcode.util.ActivityUtils
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.layout_toolbar.*


class WebActivity : BaseActivity(), View.OnClickListener {

    companion object {
        const val KEY_URL = "url"
        const val KEY_TITLE = "title"
        fun startWebActivity(url: String, title: String) {
            val bundle = Bundle()
            bundle.putString(KEY_URL, url)
            bundle.putString(KEY_TITLE, title)
            ActivityUtils.startActivity(bundle, WebActivity::class.java)
        }
    }

    private lateinit var agentWeb: AgentWeb

    override fun getLayoutRes(): Int = R.layout.activity_web
    override fun initView() {
        immersionBar.statusBarDarkFont(true).init()
        tool_bar.setNavigationOnClickListener {
            onBackPressedSupport()
        }
        val bundle = intent.extras
        val url = bundle?.getString(KEY_URL, "")
        val title = bundle?.getString(KEY_TITLE)
        tv_title.text = title
        agentWeb = url?.getAgentWeb(this, container)!!
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (agentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun initData() {
    }

    override fun reConnect() {
    }


    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }

    override fun onPause() {
        agentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        agentWeb.webLifeCycle.onResume()
        super.onResume()
    }


    override fun onDestroy() {
        super.onDestroy()
        //mAgentWeb.destroy();
        agentWeb.webLifeCycle.onDestroy()
    }
}