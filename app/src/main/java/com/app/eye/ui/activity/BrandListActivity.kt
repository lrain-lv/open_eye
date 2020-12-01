package com.app.eye.ui.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.eye.R
import com.app.eye.base.mvvm.BaseVMActivity
import com.app.eye.rx.actionUrlToRequest
import com.app.eye.ui.adapter.BrandListAdapter
import com.app.eye.ui.entity.BrandListItemX
import com.app.eye.ui.mvvm.factory.InjectorUtil
import com.app.eye.ui.mvvm.viewmodel.BrandWallViewModel
import com.app.eye.widgets.STATUS_CONTENT
import com.app.eye.widgets.STATUS_EMPTY
import com.app.eye.widgets.STATUS_ERROR
import com.app.eye.widgets.STATUS_LOADING
import com.app.eye.widgets.sidebar.WaveSideBar
import com.blankj.utilcode.util.StringUtils
import kotlinx.android.synthetic.main.activity_brand_list.*
import kotlinx.android.synthetic.main.activity_brand_list.recycler_view
import kotlinx.android.synthetic.main.activity_brand_list.status_view

class BrandListActivity : BaseVMActivity(), WaveSideBar.OnSelectIndexItemListener {

    private val listAdapter = BrandListAdapter(mutableListOf())

    var list = mutableListOf<BrandListItemX>()

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            InjectorUtil.getBrandWallVMFactory()
        ).get(BrandWallViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.activity_brand_list

    override fun initView() {
        immersionBar.fitsSystemWindows(true)
            .statusBarDarkFont(true)
            .statusBarColor(R.color.white)
            .init()
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = listAdapter
        tool_bar.setNavigationOnClickListener { onBackPressedSupport() }
        listAdapter.setOnItemClickListener { adapter, view, position ->
            val item = listAdapter.getItem(position)
            if (item.itemType == 1) {
                val name = item.name
                val link = item.link
                BrandDetailActivity.startActivity(link!!.actionUrlToRequest().params.resource_id.toString())
            }
        }

        side_bar.setOnSelectIndexItemListener(this)
    }

    override fun initData() {
        recycler_view.post {
            viewModel.getBrandList()
        }
    }

    override fun startObserver() {
        viewModel.statusLiveData.observe(this, Observer {
            when (it) {
                STATUS_LOADING -> {
                    status_view.showLoadingView()
                }
                STATUS_ERROR -> {
                    status_view.showErrorView()
                }
                STATUS_EMPTY -> {
                    status_view.showEmptyView()
                }
                STATUS_CONTENT -> {
                    status_view.showContentView()
                }
            }
        })
        viewModel.brandListLiveData.observe(this, Observer {
            listAdapter.setList(it)
            list = it
        })
        viewModel.indexListLiveData.observe(this, Observer {
            side_bar.setIndexItems(*it.toTypedArray())
        })
    }

    override fun onSelectIndexItem(index: String?) {
        list.forEachIndexed { i, brandListItemX ->
            if (StringUtils.equalsIgnoreCase(index, brandListItemX.name)) {
                ((recycler_view.layoutManager) as LinearLayoutManager).scrollToPositionWithOffset(
                    i,
                    0
                )
            }
        }
    }
}