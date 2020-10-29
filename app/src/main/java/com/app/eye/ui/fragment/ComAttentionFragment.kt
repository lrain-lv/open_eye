package com.app.eye.ui.fragment

import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseMvpFragment
import com.app.eye.rx.urlToMap
import com.app.eye.ui.adapter.AttentionAdapter
import com.app.eye.ui.mvp.contract.CommunityContract
import com.app.eye.ui.mvp.model.entity.ComAttentionEntity
import com.app.eye.ui.mvp.model.entity.ComRecEntity
import com.app.eye.ui.mvp.presenter.CommunityPresenter
import com.app.eye.widgets.MultipleStatusView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_com_attention.*

class ComAttentionFragment : BaseMvpFragment<CommunityContract.Presenter, CommunityContract.View>(),
    CommunityContract.View, SwipeRefreshLayout.OnRefreshListener,
    MultipleStatusView.OnRetryClickListener, OnLoadMoreListener, OnItemClickListener {
    companion object {
        @JvmStatic
        fun newInstance() =
            ComAttentionFragment().apply {

            }
    }

    private var nextPageUrl: String? = ""

    private var isRefresh: Boolean = true
    private lateinit var attentionAdapter: AttentionAdapter
    override fun getLayoutRes(): Int = R.layout.fragment_com_attention

    override fun reConnect() {
    }

    override fun initView() {
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        attentionAdapter = AttentionAdapter(mutableListOf())
        attentionAdapter.loadMoreModule.setOnLoadMoreListener(this)
        attentionAdapter.loadMoreModule.isEnableLoadMore = false
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = attentionAdapter
        attentionAdapter.setOnItemClickListener(this)
    }

    override fun initData() {
        isRefresh = true
        status_view.showLoadingView()
        refresh_layout.post {
            mPresenter?.getComAttentionRequest(
                hashMapOf()
            )
        }
    }


    override fun createPresenter(): CommunityContract.Presenter? = CommunityPresenter()

    override fun setComRecResponse(comRecEntity: ComRecEntity?) {
    }

    override fun setComAttentionResponse(entity: ComAttentionEntity?) {
        entity ?: status_view.showEmptyView()
        nextPageUrl = entity?.nextPageUrl
        if (isRefresh) {
            attentionAdapter.loadMoreModule.isEnableLoadMore = true
            attentionAdapter.setList(entity?.itemList)
            if (TextUtils.isEmpty(nextPageUrl)) attentionAdapter.loadMoreModule.loadMoreEnd()
        } else {
            attentionAdapter.loadMoreModule.isEnableLoadMore = true
            attentionAdapter.addData(entity?.itemList!!)
            if (TextUtils.isEmpty(nextPageUrl)) {
                attentionAdapter.loadMoreModule.loadMoreEnd()
            } else {
                attentionAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        refresh_layout.isEnabled = true
    }

    override fun hideLoading() {
        refresh_layout.isRefreshing = false
        status_view.showContentView()
    }

    override fun onRefresh() {
        attentionAdapter.loadMoreModule.isEnableLoadMore = false
        isRefresh = true
        mPresenter?.getComAttentionRequest(
            hashMapOf()
        )
    }

    override fun onRetryClick() {
    }

    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            refresh_layout.isEnabled = false
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            mPresenter?.getComAttentionRequest(map)
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }
}