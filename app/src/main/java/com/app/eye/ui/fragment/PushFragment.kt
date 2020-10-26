package com.app.eye.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseMvpFragment
import com.app.eye.event.ChangeTabEvent
import com.app.eye.rx.formToMap
import com.app.eye.ui.activity.WebActivity
import com.app.eye.ui.adapter.PushMessageAdapter
import com.app.eye.ui.mvp.contract.PushContract
import com.app.eye.ui.mvp.model.entity.PushEntity
import com.app.eye.ui.mvp.presenter.PushPresenter
import com.app.eye.widgets.MultipleStatusView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_push.*
import org.greenrobot.eventbus.EventBus

class PushFragment : BaseMvpFragment<PushContract.Presenter, PushContract.View>(),
    PushContract.View, OnItemClickListener, OnLoadMoreListener,
    SwipeRefreshLayout.OnRefreshListener,
    MultipleStatusView.OnRetryClickListener {


    private lateinit var pushMessageAdapter: PushMessageAdapter

    private var map = hashMapOf<String, String>()

    private var isRefresh = true

    private var start = 0

    override fun getLayoutRes(): Int = R.layout.fragment_push

    override fun reConnect() {

    }

    override fun initView() {
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        pushMessageAdapter = PushMessageAdapter(mutableListOf())
        pushMessageAdapter.setOnItemClickListener(this)
        status_view.setOnRetryClickListener(this)
        pushMessageAdapter.loadMoreModule.setOnLoadMoreListener(this)
        pushMessageAdapter.loadMoreModule.isEnableLoadMore = false
        recycler_view.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = pushMessageAdapter

    }

    override fun initData() {
        status_view.showLoadingView()
        recycler_view.post {
            isRefresh = true
            start = 0
            map["start"] = start.toString()
            map["num"] = "10"
            mPresenter?.getPushRequest(map)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PushFragment().apply {
            }
    }

    override fun createPresenter(): PushContract.Presenter? = PushPresenter()
    override fun setPushResponse(pushEntity: PushEntity?) {
        pushEntity ?: status_view.showEmptyView()

        when (isRefresh) {
            true -> {
                pushMessageAdapter.loadMoreModule.isEnableLoadMore =
                    !pushEntity!!.nextPageUrl.isNullOrEmpty()
                pushMessageAdapter.setList(pushEntity.messageList)
            }
            false -> {
                pushMessageAdapter.loadMoreModule.isEnableLoadMore =
                    !pushEntity!!.nextPageUrl.isNullOrEmpty()
                pushMessageAdapter.addData(pushEntity.messageList)
                if (pushEntity.nextPageUrl.isNullOrEmpty()) pushMessageAdapter.loadMoreModule.loadMoreEnd()
                else pushMessageAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        refresh_layout.isEnabled = true
    }

    override fun showLoading() {
    }


    override fun hideLoading() {
        status_view.showContentView()
        refresh_layout.isRefreshing = false
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = pushMessageAdapter.getItem(position)
        if (item.actionUrl.contains("homepage")) {
            EventBus.getDefault().post(ChangeTabEvent())
        } else if (item.actionUrl.contains("webview")) {
            val indexOf = item.actionUrl.indexOf("?")
            val substring = item.actionUrl.substring(indexOf + 1)
            val urlToMap = substring.formToMap()
            WebActivity.startWebActivity(
                url = urlToMap["url"] ?: error(""),
                title = urlToMap["title"] ?: error("")
            )
        }
    }

    override fun onLoadMore() {
        refresh_layout.isEnabled = false
        isRefresh = false
        start += 10
        map["start"] = start.toString()
        map["num"] = "10"
        mPresenter?.getPushRequest(map)
    }

    override fun onRefresh() {
        pushMessageAdapter.loadMoreModule.isEnableLoadMore = false
        isRefresh = true
        start = 0
        map["start"] = start.toString()
        map["num"] = "10"
        mPresenter?.getPushRequest(map)
    }


    override fun onRetryClick() {
        refresh_layout.isRefreshing = false
        refresh_layout.isEnabled = false
        status_view.showLoadingView()
        onRefresh()
    }

}