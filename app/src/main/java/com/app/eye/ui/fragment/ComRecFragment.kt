package com.app.eye.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseMvpFragment
import com.app.eye.rx.urlToMap
import com.app.eye.ui.activity.TopicSquareActivity
import com.app.eye.ui.adapter.BannerItemAdapter
import com.app.eye.ui.adapter.ComRecAdapter
import com.app.eye.ui.adapter.SquareCardAdapter
import com.app.eye.ui.mvp.contract.CommunityContract
import com.app.eye.ui.mvp.model.entity.ComAttentionEntity
import com.app.eye.ui.mvp.model.entity.ComRecEntity
import com.app.eye.ui.mvp.model.entity.ItemX
import com.app.eye.ui.mvp.presenter.CommunityPresenter
import com.app.eye.widgets.MultipleStatusView
import com.app.eye.widgets.StaggeredDividerItemDecoration
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.fragment_rec.*

class ComRecFragment : BaseMvpFragment<CommunityContract.Presenter, CommunityContract.View>(),
    CommunityContract.View, SwipeRefreshLayout.OnRefreshListener,
    MultipleStatusView.OnRetryClickListener, OnLoadMoreListener {
    companion object {
        @JvmStatic
        fun newInstance() =
            ComRecFragment().apply {
            }
    }

    private var map = hashMapOf<String, String>()

    private lateinit var headerView: View
    private lateinit var banner: Banner<ItemX, BannerItemAdapter>
    private lateinit var recyclerHeader: RecyclerView

    private lateinit var comRecAdapter: ComRecAdapter

    private lateinit var squareCardAdapter: SquareCardAdapter
    private lateinit var bannerItemAdapter: BannerItemAdapter

    private var isRefresh: Boolean = true

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
//        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                if (!_mActivity.isFinishing) {
//                    if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
//                        Glide.with(_mActivity)
//                            .resumeRequests()
//                    } else {
//                        Glide.with(_mActivity)
//                            .pauseRequests()
//                    }
//                }
//            }
//        })
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
            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        squareCardAdapter = SquareCardAdapter(mutableListOf())
        recyclerHeader.adapter = squareCardAdapter
        squareCardAdapter.setOnItemClickListener { adapter, view, position ->
            val item = squareCardAdapter.getItem(position)
            val title = item.data.title
            TopicSquareActivity.startTopicSquareActivity(title)
        }
    }

    override fun initData() {
        status_view.showLoadingView()
        recycler_view.post {
            mPresenter?.getRecRequest(isRefresh, hashMapOf())
        }
    }

    override fun createPresenter(): CommunityContract.Presenter? = CommunityPresenter()

    override fun setComRecResponse(comRecEntity: ComRecEntity?) {
        comRecEntity ?: status_view.showEmptyView()
        if (isRefresh) {
            if (!comRecAdapter.hasHeaderLayout()) {
                val squareItem = comRecEntity?.itemList?.get(0)
                squareCardAdapter.setList(squareItem!!.data.itemList)
                val bannerItem = comRecEntity.itemList[1]
                bannerItemAdapter.setDatas(bannerItem.data.itemList)
                comRecAdapter.addHeaderView(headerView)
            }
            val nextPageUrl = comRecEntity?.nextPageUrl
            map = nextPageUrl?.urlToMap() as HashMap<String, String>
            val itemList = comRecEntity.itemList.filter { it.type == "communityColumnsCard" }
            comRecAdapter.loadMoreModule.isEnableLoadMore =
                true
            comRecAdapter.setList(itemList)
            if (nextPageUrl.isNullOrEmpty()) comRecAdapter.loadMoreModule.loadMoreEnd()
        } else {
            val nextPageUrl = comRecEntity?.nextPageUrl
            map = nextPageUrl?.urlToMap() as HashMap<String, String>
            comRecAdapter.loadMoreModule.isEnableLoadMore = true
            comRecAdapter.addData(comRecEntity.itemList)
            if (nextPageUrl.isNullOrEmpty()) comRecAdapter.loadMoreModule.loadMoreEnd()
            else comRecAdapter.loadMoreModule.loadMoreComplete()
        }

        refresh_layout.isEnabled = true
    }

    override fun setComAttentionResponse(entity: ComAttentionEntity?) {

    }
    override fun showLoading() {
    }

    override fun hideLoading() {
        status_view.showContentView()
        refresh_layout.isRefreshing = false
    }

    override fun onRefresh() {
        isRefresh = true
        comRecAdapter.loadMoreModule.isEnableLoadMore = false
        mPresenter?.getRecRequest(isRefresh, hashMapOf())
    }

    override fun onRetryClick() {
    }

    override fun onLoadMore() {
        isRefresh = false
        refresh_layout.isEnabled = false
        mPresenter?.getRecRequest(isRefresh, map)
    }
}