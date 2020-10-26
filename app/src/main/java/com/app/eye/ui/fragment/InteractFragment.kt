package com.app.eye.ui.fragment

import com.app.eye.R
import com.app.eye.base.BaseFragment

class InteractFragment : BaseFragment() {

    override fun getLayoutRes(): Int = R.layout.fragment_interact
    override fun reConnect() {
    }

    override fun initView() {
    }

    override fun initData() {
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            InteractFragment().apply {
            }
    }
}