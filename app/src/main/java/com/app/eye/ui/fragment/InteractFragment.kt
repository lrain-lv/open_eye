package com.app.eye.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.eye.R
import com.app.eye.base.mvvm.BaseDataBindFragment
import com.app.eye.base.mvvm.BaseVMFragment
import com.app.eye.databinding.FragmentInteractBinding
import com.app.eye.rx.checkSuccess
import com.app.eye.ui.adapter.InteractAdapter
import com.app.eye.ui.mvvm.viewmodel.InteractViewModel
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.orhanobut.logger.Logger
import org.koin.androidx.viewmodel.ext.android.viewModel

class InteractFragment : BaseDataBindFragment<FragmentInteractBinding>(), OnLoadMoreListener {

    companion object {
        @JvmStatic
        fun newInstance() = InteractFragment().apply { }
    }

    private val adapter = InteractAdapter(mutableListOf())

    private val viewModel by viewModel<InteractViewModel>()

    override fun getLayoutRes(): Int = R.layout.fragment_interact

    override fun startObserver() {
        viewModel.apply {
            enableLoadMoreLiveData.observe(this@InteractFragment) {
                adapter.loadMoreModule.isEnableLoadMore = it
            }
            entityLiveData.observe(this@InteractFragment) {
                it.checkSuccess({ entity ->
                    if (viewModel.isRefresh) {
                        adapter.setList(entity.itemList)
                        if (TextUtils.isEmpty(entity.nextPageUrl)) adapter.loadMoreModule.loadMoreEnd()
                    } else {
                        adapter.addData(entity.itemList)
                        if (TextUtils.isEmpty(entity.nextPageUrl)) {
                            adapter.loadMoreModule.loadMoreEnd()
                        } else {
                            adapter.loadMoreModule.loadMoreComplete()
                        }
                    }
                }
                )
            }
        }
    }

    override fun initView() {
        adapter.apply {
            loadMoreModule.setOnLoadMoreListener(this@InteractFragment)
            loadMoreModule.isEnableLoadMore = false
        }
        binding.viewModel = this@InteractFragment.viewModel
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    override fun initData() {
        binding.recyclerView.post {
            viewModel.onRefresh(true)
        }
    }

    override fun onLoadMore() {
        viewModel.onLoadMore()
    }

}