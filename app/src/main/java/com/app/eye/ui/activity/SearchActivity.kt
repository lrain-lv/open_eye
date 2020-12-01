package com.app.eye.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.eye.R
import com.app.eye.base.mvvm.BaseVMActivity
import com.app.eye.rx.checkSuccess
import com.app.eye.ui.adapter.HotSearchAdapter
import com.app.eye.ui.adapter.SearchAdapter
import com.app.eye.ui.mvp.contract.SearchContract
import com.app.eye.ui.entity.HotSearchEntity
import com.app.eye.ui.mvp.presenter.SearchPresenter
import com.app.eye.ui.mvvm.factory.InjectorUtil
import com.app.eye.ui.mvvm.viewmodel.SearchViewModel
import com.blankj.utilcode.util.KeyboardUtils
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseVMActivity(),
    View.OnClickListener, TextWatcher, OnLoadMoreListener {

    private lateinit var hotSearchAdapter: HotSearchAdapter
    private lateinit var searchAdapter: SearchAdapter

    private var isFirst: Boolean = true

    private lateinit var originDataList: MutableList<HotSearchEntity>

    private val viewModel by lazy {
        ViewModelProvider(this, InjectorUtil.getSearchVMFactory()).get(
            SearchViewModel::class.java
        )
    }

    override fun getLayoutRes(): Int = R.layout.activity_search

    override fun initView() {
        immersionBar.statusBarDarkFont(true).init()
        tv_cancel.setOnClickListener(this)
        iv_del.setOnClickListener(this)
        hotSearchAdapter = HotSearchAdapter(mutableListOf())
        searchAdapter = SearchAdapter(mutableListOf())
        recycler_view_result.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        recycler_view_result.setHasFixedSize(true)
        recycler_view_result.adapter = searchAdapter
        searchAdapter.loadMoreModule.setOnLoadMoreListener(this)
        searchAdapter.loadMoreModule.isEnableLoadMore = false
        recycler_view_result.visibility = View.GONE
        recycler_view.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = hotSearchAdapter
        hotSearchAdapter.addChildClickViewIds(R.id.tv_hot, R.id.tv_del)
        hotSearchAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.tv_hot -> {
                    val item = hotSearchAdapter.getItem(position)
                    if (!item.isKeyWord) return@setOnItemChildClickListener

                    et_search.removeTextChangedListener(this)
                    et_search.setText(if (item.isSearch) item.searchResult else item.keyword)
                    KeyboardUtils.hideSoftInput(et_search)
                    status_view.showLoadingView()
                    iv_del.visibility = View.VISIBLE
                    viewModel.doSearch(et_search.text.toString())
                    isFirst = true
                }
                R.id.tv_del -> {
                    val dataList = hotSearchAdapter.data.filter { !it.isHistory }
                    originDataList = dataList as MutableList<HotSearchEntity>
                    hotSearchAdapter.setList(dataList)
                }
            }
        }
        et_search.addTextChangedListener(this)
    }

    override fun initData() {
        status_view.showLoadingView()
        recycler_view.post {
            KeyboardUtils.showSoftInput(et_search)
            viewModel.getHotData()
        }
    }

    override fun startObserver() {
        viewModel.hotLiveData.observe(this, Observer {
            status_view.showContentView()
            recycler_view.visibility = View.VISIBLE
            recycler_view_result.visibility = View.GONE
            originDataList = it
            hotSearchAdapter.setList(it)
        })
        viewModel.preSearchLiveData.observe(this, Observer {
            status_view.showContentView()
            recycler_view.visibility = View.VISIBLE
            recycler_view_result.visibility = View.GONE
            if (it.isNotEmpty()) {
                hotSearchAdapter.setList(it)
            }
        })
        viewModel.searchEntityLiveData.observe(this, Observer {
            it.checkSuccess({ searchEntity ->
                status_view.showContentView()
                searchAdapter.loadMoreModule.isEnableLoadMore = true
                if (isFirst) {
                    searchAdapter.setList(searchEntity.itemList)
                    searchAdapter.loadMoreModule.loadMoreEnd()
                }
                recycler_view.visibility = View.GONE
                recycler_view_result.visibility = View.VISIBLE
            })
        })
    }

    override fun reConnect() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_cancel -> {
                KeyboardUtils.hideSoftInput(et_search)
                onBackPressedSupport()
            }
            R.id.iv_del -> {
                et_search.setText("")
                recycler_view.visibility = View.VISIBLE
                recycler_view_result.visibility = View.GONE
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
        val text = s.toString().trim()
        if (text.isEmpty()) iv_del.visibility = View.GONE else iv_del.visibility =
            View.VISIBLE
        if (text.isEmpty()) {
            hotSearchAdapter.setList(originDataList)
            return
        }
        viewModel.doPreSearchData(text)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun onLoadMore() {
    }

    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
        overridePendingTransition(R.anim.top_slient, R.anim.out_from_top)
    }
}