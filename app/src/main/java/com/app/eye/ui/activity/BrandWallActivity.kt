package com.app.eye.ui.activity

import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.mvvm.BaseDataBindActivity
import com.app.eye.base.mvvm.BaseVMActivity
import com.app.eye.databinding.ActivityBrandWallBinding
import com.app.eye.rx.checkSuccess
import com.app.eye.rx.getAgentWeb
import com.app.eye.ui.adapter.BrandWallAdapter
import com.app.eye.ui.adapter.BrandWallHeaderAdapter
import com.app.eye.ui.entity.ToolbarTitle
import com.app.eye.ui.mvvm.viewmodel.BrandWallViewModel
import com.app.eye.widgets.STATUS_CONTENT
import com.app.eye.widgets.STATUS_EMPTY
import com.app.eye.widgets.STATUS_ERROR
import com.app.eye.widgets.STATUS_LOADING
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.activity_brand_wall.*
import kotlinx.android.synthetic.main.activity_brand_wall.recycler_view
import kotlinx.android.synthetic.main.activity_brand_wall.refresh_layout
import kotlinx.android.synthetic.main.activity_brand_wall.status_view
import org.koin.androidx.viewmodel.ext.android.viewModel

class BrandWallActivity : BaseDataBindActivity(),
    OnLoadMoreListener {

    private val brandAdapter = BrandWallAdapter(mutableListOf())
    private val headerAdapter = BrandWallHeaderAdapter(mutableListOf())

    private val viewModel by viewModel<BrandWallViewModel>()
    private val binding by binding<ActivityBrandWallBinding>(R.layout.activity_brand_wall)

    private lateinit var container: FrameLayout
    private lateinit var recyclerHeader: RecyclerView
    private lateinit var tvTitle: TextView
    private lateinit var tvRightText: TextView

    override fun initView() {
        immersionBar.statusBarDarkFont(true)
            .fitsSystemWindows(true)
            .statusBarColor(R.color.white)
            .init()
        binding.apply {
            viewModel = this@BrandWallActivity.viewModel
            toolbarTitle = ToolbarTitle(R.mipmap.icon_back) {
                onBackPressedSupport()
            }
            colorResId = R.array.google_colors
        }
        initHeader()
        brandAdapter.loadMoreModule.setOnLoadMoreListener(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = brandAdapter
        brandAdapter.setOnItemClickListener { adapter, view, position ->
            val item = brandAdapter.getItem(position)
            val videoId = item.metro_data.video_id
            VideoDetailActivity.startActivity(videoId.toString())
        }
    }

    private fun initHeader() {
        val headerView =
            layoutInflater.inflate(R.layout.lauyout_brand_wall_header, recycler_view, false)
        container = headerView.findViewById<FrameLayout>(R.id.container)
        val view = headerView.findViewById<View>(R.id.view3)
        view.setOnClickListener {
            ActivityUtils.startActivity(BrandListActivity::class.java)
        }
        recyclerHeader = headerView.findViewById<RecyclerView>(R.id.recycler_header)
        tvTitle = headerView.findViewById<TextView>(R.id.tv_title)
        tvRightText = headerView.findViewById<TextView>(R.id.tv_right_text)
        recyclerHeader.apply {
            layoutManager =
                LinearLayoutManager(this@BrandWallActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = headerAdapter
        }
        headerAdapter.setOnItemClickListener { _, _, position ->
            val item = headerAdapter.getItem(position)
            val videoId = item.metro_data.video_id
            VideoDetailActivity.startActivity(videoId.toString())
        }
        brandAdapter.addHeaderView(headerView)
    }

    override fun initData() {
        recycler_view.post {
            viewModel.onRefresh(isFirst = true)
        }
    }

    override fun startObserver() {
        viewModel.apply {
            refreshLiveData.observe(this@BrandWallActivity) {
                refresh_layout.isRefreshing = it
            }
            enableLoadMoreLiveData.observe(this@BrandWallActivity) {
                brandAdapter.loadMoreModule.isEnableLoadMore = it
            }
            brandWallLiveData.observe(this@BrandWallActivity) {
                it.checkSuccess({ entity ->
                    if (viewModel.isRefresh) {
                        val cardList = entity.result.card_list
                        cardList.forEach {
                            val type = it.type
                            when {
                                StringUtils.equals("set_metro_list", type) -> {
                                    brandAdapter.setList(it.card_data.body.metro_list)
                                }
                                StringUtils.equals("web_card", type) -> {
                                    val url = it.card_data.body.metro_list[0].metro_data.url
                                    url.getAgentWeb(this@BrandWallActivity, container)
                                }
                                StringUtils.equals("set_slide_metro_list", type) -> {
                                    val brandLeft = it.card_data.header.left[0]
                                    val brandRight = it.card_data.header.right[0]
                                    tvTitle.text = brandLeft.metro_data.text
                                    tvRightText.text = brandRight.metro_data.text
                                    tvRightText.setOnClickListener {
                                        val link = brandRight.metro_data.link
                                        val index = link.indexOf("?")
                                        val lastIndex = link.lastIndexOf("/")
                                        val id = link.substring(lastIndex + 1, index)
                                        LightTopicActivity.startActivity(
                                            brandLeft.metro_data.text,
                                            id = id.toInt()
                                        )
                                    }
                                    val metroList = it.card_data.body.metro_list
                                    headerAdapter.setList(metroList)
                                }
                            }
                        }
                    } else {
                        brandAdapter.addData(entity.result.item_list)
                        if (entity.result.last_item_id.isNullOrEmpty()) {
                            brandAdapter.loadMoreModule.loadMoreEnd()
                        } else {
                            brandAdapter.loadMoreModule.loadMoreComplete()
                        }
                    }

                })
            }
        }
    }

    override fun onLoadMore() {
        viewModel.onLoadMore()
    }
}