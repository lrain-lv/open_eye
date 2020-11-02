package com.app.eye.ui.activity

import android.os.Bundle
import android.text.TextUtils
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseMvpActivity
import com.app.eye.rx.urlToMap
import com.app.eye.ui.adapter.CategoryAdapter
import com.app.eye.ui.adapter.InformationAdapter
import com.app.eye.ui.adapter.SpecialTopicAdapter
import com.app.eye.ui.mvp.contract.CategoryContract
import com.app.eye.ui.mvp.model.entity.CategoryEntity
import com.app.eye.ui.mvp.model.entity.InformationEntity
import com.app.eye.ui.mvp.model.entity.SpecialTopicEntity
import com.app.eye.ui.mvp.presenter.CategoryPresenter
import com.app.eye.widgets.itemdecoration.LayoutMarginDecoration
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.activity_category.*

/**
 * 分类 专题策划 全部资讯
 */
class CategoryActivity : BaseMvpActivity<CategoryContract.Presenter, CategoryContract.View>(),
    CategoryContract.View, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    companion object {
        fun startActivity(title: String, type: Int) {
            val bundle = Bundle().apply {
                putString("title", title)
                putInt("type", type) // 0 分类 1 策划 2 资讯
            }
            ActivityUtils.startActivity(bundle, CategoryActivity::class.java)
        }
    }

    private lateinit var title: String
    private var type: Int = 0
    private lateinit var categoryAdapter: CategoryAdapter

    private lateinit var specialTopicAdapter: SpecialTopicAdapter
    private lateinit var informationAdapter: InformationAdapter

    private var nextPageUrl: String? = ""

    private var isRefresh: Boolean = true

    override fun getLayoutRes(): Int = R.layout.activity_category

    override fun initView() {
        immersionBar.statusBarDarkFont(false).statusBarColor(R.color.black).init()
        val extras = intent.extras
        title = extras?.getString("title", "热门分类")!!
        type = extras.getInt("type", 0)
        initToolBar(tool_bar)
        tv_title.text = title
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        when (type) {
            0 -> {
                categoryAdapter = CategoryAdapter(mutableListOf())
                categoryAdapter.apply {
                    loadMoreModule.setOnLoadMoreListener(this@CategoryActivity)
                    loadMoreModule.isEnableLoadMore = false
                }
                val manager = GridLayoutManager(mContext, 2)
                manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val item = categoryAdapter.getItem(position)
                        return if (StringUtils.equals("rectangleCard", item.type))
                            2
                        else 1
                    }
                }
                recycler_view.layoutManager = manager
                recycler_view.addItemDecoration(LayoutMarginDecoration(2, SizeUtils.dp2px(2f)))
                recycler_view.adapter = categoryAdapter
                categoryAdapter.setOnItemClickListener { adapter, view, position ->
                    val item = categoryAdapter.getItem(position)
                    val actionUrl = item.data.actionUrl!!
                    when {
                        actionUrl.contains("ranklist") -> {
                            ActivityUtils.startActivity(RankActivity::class.java)
                        }
                        actionUrl.contains("campaign") -> {
                            startActivity("专题", 1)
                        }
                        actionUrl.contains("tag") -> {
                            val s = actionUrl.substring(17)
                            val id = s.substring(0, s.indexOf("/"))
                            TagVideoActivity.startActivity(
                                id,
                                item.data.title,
                                item.data.image,
                                item.data.description ?: "")
                        }
                    }
                }
            }
            1 -> {
                specialTopicAdapter = SpecialTopicAdapter(mutableListOf())
                specialTopicAdapter.apply {
                    loadMoreModule.setOnLoadMoreListener(this@CategoryActivity)
                    loadMoreModule.isEnableLoadMore = false
                }
                specialTopicAdapter.setOnItemClickListener { adapter, view, position ->
                    val item = specialTopicAdapter.getItem(position)
                    LightTopicActivity.startActivity(item.data.title, item.data.id)
                }
                recycler_view.layoutManager =
                    LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
                recycler_view.adapter = specialTopicAdapter
            }
            2 -> {
                informationAdapter = InformationAdapter(mutableListOf())
                informationAdapter.apply {
                    loadMoreModule.setOnLoadMoreListener(this@CategoryActivity)
                    loadMoreModule.isEnableLoadMore = false
                }
                recycler_view.layoutManager =
                    LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
                recycler_view.adapter = informationAdapter
            }
        }
    }

    override fun initData() {
        recycler_view.post {
            when (type) {
                0 -> {
                    mPresenter?.getCategoryRequest()
                }
                1 -> {
                    isRefresh = true
                    mPresenter?.getSpecialTopicRequest(hashMapOf())
                }
                2 -> {
                    isRefresh = true
                    mPresenter?.getInformationRequest(hashMapOf())
                }
            }
        }
    }

    override fun reConnect() {
    }

    override fun createPresenter(): CategoryContract.Presenter? = CategoryPresenter()

    override fun setCategoryResponse(data: CategoryEntity?) {
        data ?: status_view.showEmptyView()
        categoryAdapter.loadMoreModule.isEnableLoadMore = true
        categoryAdapter.setList(data?.itemList)
        categoryAdapter.loadMoreModule.loadMoreEnd()
    }

    override fun setSpecialTopicResponse(data: SpecialTopicEntity?) {
        data ?: status_view.showEmptyView()
        nextPageUrl = data?.nextPageUrl
        if (isRefresh) {
            specialTopicAdapter.loadMoreModule.isEnableLoadMore = true
            specialTopicAdapter.setList(data?.itemList)
            if (TextUtils.isEmpty(nextPageUrl)) specialTopicAdapter.loadMoreModule.loadMoreEnd()
        } else {
            specialTopicAdapter.loadMoreModule.isEnableLoadMore = true
            specialTopicAdapter.addData(data?.itemList!!)
            if (TextUtils.isEmpty(nextPageUrl)) {
                specialTopicAdapter.loadMoreModule.loadMoreEnd()
            } else {
                specialTopicAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        refresh_layout.isEnabled = true
    }

    override fun setInformationResponse(data: InformationEntity?) {
        data ?: status_view.showEmptyView()
        nextPageUrl = data?.nextPageUrl
        if (isRefresh) {
            informationAdapter.loadMoreModule.isEnableLoadMore = true
            informationAdapter.setList(data?.itemList)
            if (TextUtils.isEmpty(nextPageUrl)) informationAdapter.loadMoreModule.loadMoreEnd()
        } else {
            informationAdapter.loadMoreModule.isEnableLoadMore = true
            informationAdapter.addData(data?.itemList!!)
            if (TextUtils.isEmpty(nextPageUrl)) {
                informationAdapter.loadMoreModule.loadMoreEnd()
            } else {
                informationAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        refresh_layout.isEnabled = true
    }

    override fun onRefresh() {
        when (type) {
            0 -> {
                mPresenter?.getCategoryRequest()
            }
            1 -> {
                isRefresh = true
                mPresenter?.getSpecialTopicRequest(hashMapOf())
            }
            2 -> {
                isRefresh = true
                mPresenter?.getInformationRequest(hashMapOf())
            }
        }
    }

    override fun onLoadMore() {

        when (type) {
            1 -> {
                if (!nextPageUrl.isNullOrEmpty()) {
                    refresh_layout.isEnabled = false
                    isRefresh = false
                    val map = nextPageUrl!!.urlToMap()
                    mPresenter?.getSpecialTopicRequest(map)
                }
            }
            2 -> {
                if (!nextPageUrl.isNullOrEmpty()) {
                    refresh_layout.isEnabled = false
                    isRefresh = false
                    val map = nextPageUrl!!.urlToMap()
                    mPresenter?.getInformationRequest(map)
                }
            }
        }
    }

    override fun hideLoading() {
        status_view.showContentView()
        refresh_layout.isRefreshing = false
    }

}