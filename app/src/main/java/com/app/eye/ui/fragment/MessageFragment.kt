package com.app.eye.ui.fragment

import com.app.eye.R
import com.app.eye.base.BaseFragment

class MessageFragment : BaseFragment() {


    override fun getLayoutRes(): Int = R.layout.fragment_message

    override fun reConnect() {
    }

    override fun initView() {
    }

    override fun initData() {
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MessageFragment().apply {
            }
    }
}