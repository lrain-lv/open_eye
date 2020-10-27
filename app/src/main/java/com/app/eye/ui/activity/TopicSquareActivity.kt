package com.app.eye.ui.activity

import android.os.Bundle
import android.view.View
import com.app.eye.R
import com.app.eye.base.BaseActivity
import com.app.eye.ui.fragment.TopicListFragment
import com.app.eye.ui.fragment.TopicSquareFragment
import com.blankj.utilcode.util.ActivityUtils
import kotlinx.android.synthetic.main.activity_topic_square.*
import kotlinx.android.synthetic.main.layout_toolbar.tool_bar
import kotlinx.android.synthetic.main.layout_toolbar.tv_title

class TopicSquareActivity : BaseActivity() {

    companion object {
        fun startTopicSquareActivity(title: String = "") {
            val bundle = Bundle()
            bundle.putString("title", title)
            ActivityUtils.startActivity(bundle, TopicSquareActivity::class.java)
        }
    }

    private lateinit var topicSquareFragment: TopicSquareFragment
    private lateinit var topicListFragment: TopicListFragment
    private var title: String? = ""
    private val isTopic: Boolean by lazy { title?.contains("主题")!! }

    override fun getLayoutRes(): Int = R.layout.activity_topic_square

    override fun initView() {
        immersionBar.statusBarDarkFont(true)
            .statusBarColor(R.color.container_bg)
            .init()
        val extras = intent.extras
        if (extras!!.containsKey("title")) {
            title = extras.getString("title", "")
        }
        if (title.isNullOrEmpty()) onBackPressedSupport()
        tv_title.text = title
        tool_bar.setNavigationOnClickListener { onBackPressedSupport() }
        if (isTopic) iv_search.visibility =
            View.VISIBLE else iv_search.visibility = View.GONE
        if (isTopic) {
            topicSquareFragment = TopicSquareFragment.newInstance()
            loadRootFragment(R.id.container, topicSquareFragment)
        } else {
            topicListFragment = TopicListFragment.newInstance()
            loadRootFragment(R.id.container, topicListFragment)
        }
    }

    override fun initData() {
    }

    override fun reConnect() {
    }


}