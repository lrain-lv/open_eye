package com.app.eye.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.eye.R
import com.app.eye.base.BaseActivity
import com.app.eye.base.BaseMvpActivity
import com.app.eye.http.RetrofitManager
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.adapter.HotSearchAdapter
import com.app.eye.ui.adapter.SearchAdapter
import com.app.eye.ui.mvp.contract.SearchContract
import com.app.eye.ui.mvp.model.entity.HotSearchEntity
import com.app.eye.ui.mvp.model.entity.SearchEntity
import com.app.eye.ui.mvp.presenter.SearchPresenter
import com.blankj.utilcode.util.CacheDiskUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.activity_search.*
import java.util.concurrent.TimeUnit

class SearchActivity : BaseMvpActivity<SearchContract.Presenter, SearchContract.View>(),
    View.OnClickListener, SearchContract.View, TextWatcher, OnLoadMoreListener {

    private lateinit var hotSearchAdapter: HotSearchAdapter
    private lateinit var searchAdapter: SearchAdapter

    private var isFirst: Boolean = true

    private lateinit var originDataList: MutableList<HotSearchEntity>

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
                    mPresenter?.doSearch(et_search.text.toString())
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
            mPresenter?.getHotData()
        }
    }

    override fun reConnect() {

    }

    override fun createPresenter(): SearchContract.Presenter? = SearchPresenter()

    override fun setHotSearchData(dataList: MutableList<HotSearchEntity>) {
        status_view.showContentView()
        recycler_view.visibility = View.VISIBLE
        recycler_view_result.visibility = View.GONE
        originDataList = dataList
        hotSearchAdapter.setList(dataList)
    }

    override fun setPreSearchResult(dataList: MutableList<HotSearchEntity>) {
        status_view.showContentView()
        recycler_view.visibility = View.VISIBLE
        recycler_view_result.visibility = View.GONE
        if (dataList.isNotEmpty()) {
            hotSearchAdapter.setList(dataList)
        }
    }

    override fun setSearchResult(searchEntity: SearchEntity) {
        et_search.addTextChangedListener(this)
        status_view.showContentView()
        searchAdapter.loadMoreModule.isEnableLoadMore = true
        if (isFirst) {
            searchAdapter.setList(searchEntity.itemList)
            searchAdapter.loadMoreModule.loadMoreEnd()
        }
        recycler_view.visibility = View.GONE
        recycler_view_result.visibility = View.VISIBLE
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
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
        mPresenter?.doPreSearchData(text)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun onLoadMore() {
    }

    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
        overridePendingTransition(R.anim.top_slient,R.anim.out_from_top)
    }
}