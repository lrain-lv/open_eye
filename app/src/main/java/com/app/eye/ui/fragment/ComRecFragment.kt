package com.app.eye.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseMvpFragment
import com.app.eye.ui.adapter.BannerItemAdapter
import com.app.eye.ui.adapter.ComRecAdapter
import com.app.eye.ui.mvp.contract.CommunityContract
import com.app.eye.ui.mvp.model.entity.ComRecEntity
import com.app.eye.ui.mvp.model.entity.ItemX
import com.app.eye.ui.mvp.presenter.CommunityPresenter
import com.app.eye.widgets.MultipleStatusView
import com.blankj.utilcode.util.SizeUtils
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.fragment_rec.*

class ComRecFragment : BaseMvpFragment<CommunityContract.Presenter, CommunityContract.View>(),
    CommunityContract.View, SwipeRefreshLayout.OnRefreshListener,
    MultipleStatusView.OnRetryClickListener {
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

    private var isRefresh : Boolean = true

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
        recycler_view.addItemDecoration(LayoutMarginDecoration(2, SizeUtils.dp2px(5f)))
        initHeader()
        recycler_view.adapter = comRecAdapter
    }

    private fun initHeader() {
        headerView =
            layoutInflater.inflate(R.layout.layout_com_rec_header, recycler_view, false)
        recyclerHeader = headerView.findViewById(R.id.recycler_header)
        recyclerHeader.layoutManager = LinearLayoutManager(mContext)
        banner = headerView.findViewById(R.id.banner)
        banner.addBannerLifecycleObserver(this)
    }

    override fun initData() {
        status_view.showLoadingView()
        recycler_view.post {
            mPresenter?.getRecRequest(true, map)
        }
    }

    override fun createPresenter(): CommunityContract.Presenter? = CommunityPresenter()

    override fun setComRecResponse(comRecEntity: ComRecEntity?) {
        comRecEntity ?: status_view.showEmptyView()
//        val itemList = comRecEntity?.itemList?.filter { it.type == "communityColumnsCard" }
        comRecAdapter.setList(comRecEntity?.itemList)
    }

    override fun setComAttentionResponse() {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
        status_view.showContentView()
        refresh_layout.isRefreshing = false
    }

    override fun onRefresh() {
        mPresenter?.getRecRequest(true,map)
    }

    override fun onRetryClick() {
    }
}