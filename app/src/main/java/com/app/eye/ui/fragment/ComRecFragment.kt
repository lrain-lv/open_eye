package com.app.eye.ui.fragment

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.mvvm.BaseVMFragment
import com.app.eye.rx.checkSuccess
import com.app.eye.rx.urlToMap
import com.app.eye.ui.activity.GalleryActivity
import com.app.eye.ui.activity.TopicSquareActivity
import com.app.eye.ui.adapter.BannerItemAdapter
import com.app.eye.ui.adapter.ComRecAdapter
import com.app.eye.ui.adapter.SquareCardAdapter
import com.app.eye.ui.mvp.contract.CommunityContract
import com.app.eye.ui.entity.ComItem
import com.app.eye.ui.entity.ComRecEntity
import com.app.eye.ui.entity.ItemX
import com.app.eye.ui.mvp.presenter.CommunityPresenter
import com.app.eye.ui.mvvm.factory.InjectorUtil
import com.app.eye.ui.mvvm.viewmodel.CommunityViewModel
import com.app.eye.widgets.*
import com.app.eye.widgets.itemdecoration.LayoutMarginDecoration
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.fragment_rec.recycler_view
import kotlinx.android.synthetic.main.fragment_rec.refresh_layout
import kotlinx.android.synthetic.main.fragment_rec.status_view

class ComRecFragment : BaseVMFragment(), SwipeRefreshLayout.OnRefreshListener,
    MultipleStatusView.OnRetryClickListener, OnLoadMoreListener {
    companion object {
        @JvmStatic
        fun newInstance() =
            ComRecFragment().apply {
            }
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            InjectorUtil.getCommunityVMFactory()
        ).get(CommunityViewModel::class.java)
    }

    private var map = hashMapOf<String, String>()

    private lateinit var headerView: View
    private lateinit var banner: Banner<ItemX, BannerItemAdapter>
    private lateinit var recyclerHeader: RecyclerView

    private lateinit var comRecAdapter: ComRecAdapter

    private lateinit var squareCardAdapter: SquareCardAdapter
    private lateinit var bannerItemAdapter: BannerItemAdapter

    private var isRefresh: Boolean = true

    private var data: ComRecEntity? = null

    override fun getLayoutRes(): Int = R.layout.fragment_rec

    override fun reConnect() {
    }

    override fun initView() {
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        status_view.setOnRetryClickListener(this)
        comRecAdapter = ComRecAdapter(mutableListOf())
        recycler_view.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recycler_view.addItemDecoration(
            StaggeredDividerItemDecoration(
                mContext,
                5f,
                2
            )
        )
        initHeader()
        recycler_view.adapter = comRecAdapter
        comRecAdapter.loadMoreModule.setOnLoadMoreListener(this)
        comRecAdapter.setOnItemClickListener { adapter, view, position ->
            val entity: ComRecEntity? = data!!.copy()
            val list = mutableListOf<ComItem>()
            list.addAll(entity!!.itemList)
            val newList = list.filter {
                TextUtils.equals("communityColumnsCard", it.type)
            }
            entity.itemList.clear()
            entity.itemList.addAll(newList)
            GalleryActivity.startGalleryActivity(entity, position)
        }

    }

    private fun initHeader() {
        headerView =
            layoutInflater.inflate(R.layout.layout_com_rec_header, recycler_view, false)
        initTopic()
        initBanner()
    }

    private fun initBanner() {
        banner = headerView.findViewById(R.id.banner)
        bannerItemAdapter = BannerItemAdapter(mutableListOf())
        banner.apply {
            addBannerLifecycleObserver(this@ComRecFragment)
            setAdapter(bannerItemAdapter, false)
            setBannerGalleryEffect(0, 6, 1f)
            setScrollTime(1000)
        }
    }

    private fun initTopic() {
        recyclerHeader = headerView.findViewById(R.id.recycler_header)
        recyclerHeader.layoutManager =
            GridLayoutManager(mContext, 2)
        squareCardAdapter = SquareCardAdapter(mutableListOf())
        recyclerHeader.adapter = squareCardAdapter
        if (recyclerHeader.itemDecorationCount == 0) {
            recyclerHeader.addItemDecoration(LayoutMarginDecoration(2, SizeUtils.dp2px(5f)))
        }
        squareCardAdapter.setOnItemClickListener { adapter, view, position ->
            val item = squareCardAdapter.getItem(position)
            val title = item.data.title
            TopicSquareActivity.startTopicSquareActivity(title)
        }
    }

    override fun initData() {
        status_view.showLoadingView()
        recycler_view.post {
            viewModel.onRefresh(0, hashMapOf(), isFirst = true)
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

        viewModel.comRecLiveData.observe(this, Observer {
            it.checkSuccess({ entity ->
                if (isRefresh) {
                    if (!comRecAdapter.hasHeaderLayout()) {
                        val squareItem = entity.itemList.get(0)
                        squareCardAdapter.setList(squareItem.data.itemList.take(2))
                        val bannerItem = entity.itemList[1]
                        bannerItemAdapter.setDatas(bannerItem.data.itemList)
                        comRecAdapter.addHeaderView(headerView)
                    }
                    val nextPageUrl = entity.nextPageUrl
                    map = nextPageUrl?.urlToMap() as HashMap<String, String>
                    val itemList =
                        entity.itemList.filter { item -> item.type == "communityColumnsCard" }
                    comRecAdapter.loadMoreModule.isEnableLoadMore =
                        true
                    data = entity
                    comRecAdapter.setList(itemList)
                    if (nextPageUrl.isNullOrEmpty()) comRecAdapter.loadMoreModule.loadMoreEnd()
                } else {
                    val nextPageUrl = entity?.nextPageUrl
                    map = nextPageUrl?.urlToMap() as HashMap<String, String>
                    comRecAdapter.loadMoreModule.isEnableLoadMore = true
                    comRecAdapter.addData(entity.itemList)
                    data?.itemList?.addAll(entity.itemList)
                    data?.nextPageUrl = entity.nextPageUrl
                    if (nextPageUrl.isNullOrEmpty()) comRecAdapter.loadMoreModule.loadMoreEnd()
                    else comRecAdapter.loadMoreModule.loadMoreComplete()
                }

                refresh_layout.isEnabled = true
            }, {
                status_view.showErrorView()
            })
        })
    }

    override fun onRefresh() {
        isRefresh = true
        comRecAdapter.loadMoreModule.isEnableLoadMore = false
        viewModel.onRefresh(0, hashMapOf())
    }

    override fun onRetryClick() {
        isRefresh = true
        status_view.showLoadingView()
        comRecAdapter.loadMoreModule.isEnableLoadMore = false
        viewModel.onRefresh(0, hashMapOf())
    }

    override fun onLoadMore() {
        isRefresh = false
        refresh_layout.isEnabled = false
        viewModel.onLoadMore(0, map)
    }
}