package com.app.eye.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.mvvm.BaseVMActivity
import com.app.eye.rx.checkSuccess
import com.app.eye.rx.loadImageCircle
import com.app.eye.rx.loadImageCommon
import com.app.eye.ui.adapter.BrandWallAdapter
import com.app.eye.ui.adapter.BrandWallHeaderAdapter
import com.app.eye.ui.mvvm.factory.InjectorUtil
import com.app.eye.ui.mvvm.viewmodel.BrandWallViewModel
import com.app.eye.widgets.STATUS_CONTENT
import com.app.eye.widgets.STATUS_EMPTY
import com.app.eye.widgets.STATUS_ERROR
import com.app.eye.widgets.STATUS_LOADING
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.activity_brand_detail.*
import kotlinx.android.synthetic.main.activity_brand_wall.recycler_view
import kotlinx.android.synthetic.main.activity_brand_wall.refresh_layout
import kotlinx.android.synthetic.main.activity_brand_wall.status_view

class BrandDetailActivity : BaseVMActivity(), SwipeRefreshLayout.OnRefreshListener,
    OnLoadMoreListener {


    companion object {
        fun startActivity(id: String) {
            val bundle = Bundle().apply {
                putString("id", id)
            }
            ActivityUtils.startActivity(bundle, BrandDetailActivity::class.java)
        }
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            InjectorUtil.getBrandWallVMFactory()
        ).get(BrandWallViewModel::class.java)
    }

    private var isRefresh = true

    private val brandAdapter = BrandWallAdapter(mutableListOf())

    private val headerAdapter = BrandWallHeaderAdapter(mutableListOf())

    private var paramsMap = hashMapOf<String, String>()

    private lateinit var img: ImageView
    private lateinit var imgIcon: ImageView
    private lateinit var tvText: TextView
    private lateinit var tvContent: TextView
    private lateinit var tvTitle: TextView
    private lateinit var tvTag: TextView

    private var id: String = ""

    private var distance: Int = 0

    private var isShow: Boolean = false

    override fun getLayoutRes(): Int = R.layout.activity_brand_detail

    override fun initView() {
        immersionBar.statusBarDarkFont(false)
            .statusBarAlpha(0f)
            .init()
        id = intent.extras!!.getString("id", "")
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        initHeader()
        brandAdapter.loadMoreModule.setOnLoadMoreListener(this)
        brandAdapter.loadMoreModule.isEnableLoadMore = false
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = brandAdapter
        brandAdapter.setOnItemClickListener { adapter, view, position ->
            val item = brandAdapter.getItem(position)
            val videoId = item.metro_data.video_id
            VideoDetailActivity.startActivity(videoId.toString())
        }
        iv_back.setOnClickListener { onBackPressedSupport() }
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                distance += dy
                if (distance > SizeUtils.dp2px(65f)) {
                    if (view_bg.visibility == View.GONE) {
                        immersionBar.statusBarDarkFont(true)
                            .init()
                        view_bg.visibility = View.VISIBLE
                        iv_back.setImageResource(R.mipmap.ic_back_black)
                    }
                } else {
                    if (view_bg.visibility == View.VISIBLE) {
                        immersionBar.statusBarDarkFont(false)
                            .init()
                        view_bg.visibility = View.GONE
                        iv_back.setImageResource(R.mipmap.ic_back_white)
                    }
                }
            }
        })
    }

    private fun initHeader() {
        val headerView =
            layoutInflater.inflate(R.layout.layout_brand_detail_header, recycler_view, false)
        img = headerView.findViewById<ImageView>(R.id.iv_img)
        imgIcon = headerView.findViewById<ImageView>(R.id.iv_icon)
        tvText = headerView.findViewById<TextView>(R.id.tv_text)
        tvTitle = headerView.findViewById<TextView>(R.id.tv_title)
        tvTag = headerView.findViewById<TextView>(R.id.tv_tag)
        tvContent = headerView.findViewById<TextView>(R.id.tv_content)
        brandAdapter.addHeaderView(headerView)
    }

    override fun initData() {
        val map = mapOf(
            "page_label" to "brand_wall_detail",
            "resource_id" to id,
            "page_type" to "card"
        )
        recycler_view.post {
            viewModel.onRefresh(map, isFirst = true)
        }
    }

    override fun startObserver() {
        viewModel.refreshLiveData.observe(this, Observer {
            refresh_layout.isRefreshing = it
        })
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
        viewModel.brandWallLiveData.observe(this, Observer {
            it.checkSuccess({ entity ->
                if (isRefresh) {
                    brandAdapter.loadMoreModule.isEnableLoadMore = true
                    val cardList = entity.result.card_list
                    cardList.forEachIndexed { index, brandCard ->
                        val type = brandCard.type
                        when {
                            index == 0 -> {
                                val brandMetro = brandCard.card_data.body.metro_list[0]
                                val metroData = brandMetro.metro_data
                                tvTitle.text = metroData.title
                                tvContent.text = metroData.desc
                                metroData.tags.forEach {
                                    tvTag.append("${it.title} ")
                                }
                                img.loadImageCommon(
                                    metroData.background.url
                                )
                                imgIcon.loadImageCircle(
                                    metroData.avatar.url,
                                    80f
                                )
                            }
                            index == 1 -> {
                                val cardData = brandCard.card_data
                                val header = cardData.header
                                val brandLeft = header.left[0]
                                val create = SpanUtils.with(tvText)
                                    .append(brandLeft.metro_data.text)
                                    .setBold()
                                    .setForegroundColor(Color.BLACK)
                                    .setFontSize(18, true)
                                    .append(" ")
                                    .append(brandLeft.metro_data.subtitle)
                                    .setFontSize(14, true)
                                    .setForegroundColor(Color.GRAY)
                                    .create()
                                tvText.text = create
                                val metroList = cardData.body.metro_list
                                brandAdapter.setList(metroList)
                            }
                            StringUtils.equals("call_metro_list", type) -> {
                                val apiRequest = brandCard.card_data.body.api_request
                                paramsMap["type"] = apiRequest.params.type
                                paramsMap["last_item_id"] = apiRequest.params.last_item_id
                                paramsMap["num"] = apiRequest.params.num.toString()
                                paramsMap["card"] = apiRequest.params.card
                                paramsMap["card_index"] = apiRequest.params.card_index.toString()
                                paramsMap["material_index"] =
                                    apiRequest.params.material_index.toString()
                                paramsMap["material_relative_index"] =
                                    apiRequest.params.material_relative_index.toString()
                                paramsMap["start_last_item_id"] =
                                    apiRequest.params.start_last_item_id
                                paramsMap["related_tag_id"] =
                                    apiRequest.params.related_tag_id.toString()
                            }
                        }
                    }
                } else {
                    brandAdapter.loadMoreModule.isEnableLoadMore = true
                    val lastItemId = entity.result.last_item_id
                    paramsMap["last_item_id"] = lastItemId ?: ""
                    brandAdapter.addData(entity.result.item_list)
                    if (entity.result.last_item_id.isNullOrEmpty()) {
                        brandAdapter.loadMoreModule.loadMoreEnd()
                    } else {
                        brandAdapter.loadMoreModule.loadMoreComplete()
                    }
                }
                refresh_layout.isEnabled = true
            }, onError = {
                status_view.showErrorView()
            })
        })
    }

    override fun onRefresh() {
        isRefresh = true
        brandAdapter.loadMoreModule.isEnableLoadMore = false
        val map = mapOf(
            "page_label" to "brand_wall_detail",
            "resource_id" to id,
            "page_type" to "card"
        )
        viewModel.onRefresh(map, isFirst = true)
    }

    override fun onLoadMore() {
        isRefresh = false
        refresh_layout.isEnabled = false
        viewModel.onLoadMore(paramsMap)
    }
}