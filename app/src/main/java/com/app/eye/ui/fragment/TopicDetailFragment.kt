package com.app.eye.ui.fragment

import android.os.Bundle
import com.app.eye.R
import com.app.eye.base.BaseFragment

class TopicDetailFragment : BaseFragment() {

    override fun getLayoutRes(): Int = R.layout.fragment_topic_detail

    override fun reConnect() {
    }

    override fun initView() {
    }

    override fun initData() {
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TopicDetailFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}