package com.app.eye.ui.fragment

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseMvpFragment
import com.app.eye.base.mvvm.BaseVMFragment
import com.app.eye.event.LoginEvent
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.rx.urlToMap
import com.app.eye.ui.activity.CategoryActivity
import com.app.eye.ui.activity.LoginActivity
import com.app.eye.ui.adapter.PrivateMsgAdapter
import com.app.eye.ui.mvp.contract.PushContract
import com.app.eye.ui.mvp.model.entity.MessageEntity
import com.app.eye.ui.mvp.model.entity.PushEntity
import com.app.eye.ui.mvp.model.entity.RecFriendEntity
import com.app.eye.ui.mvp.presenter.PushPresenter
import com.app.eye.ui.mvvm.factory.InjectorUtil
import com.app.eye.ui.mvvm.viewmodel.MessageViewModel
import com.app.eye.widgets.STATUS_CONTENT
import com.app.eye.widgets.STATUS_EMPTY
import com.app.eye.widgets.STATUS_ERROR
import com.app.eye.widgets.STATUS_LOADING
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SPUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_message.recycler_view
import kotlinx.android.synthetic.main.fragment_message.refresh_layout
import kotlinx.android.synthetic.main.fragment_message.status_view
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MessageFragment : BaseVMFragment(), View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener,
    OnLoadMoreListener, OnItemClickListener {

    companion object {
        @JvmStatic
        fun newInstance() =
            MessageFragment().apply {
            }
    }

    private val viewModel by lazy {
        ViewModelProvider(this, InjectorUtil.getMessageVMFactory()).get(
            MessageViewModel::class.java
        )
    }

    private val isLogin: Boolean by lazy {
        SPUtils.getInstance("eye").getString("avatar", "").isNotEmpty()
    }

    private var msgAdapter = PrivateMsgAdapter(mutableListOf())

    private var nextPageUrl: String? = ""

    private var isRefresh: Boolean = true

    override fun getLayoutRes(): Int = R.layout.fragment_message


    override fun reConnect() {
    }

    override fun initView() {
        layout_login.visibility = if (isLogin) View.GONE else View.VISIBLE
        initListener()
        initSwipeRefreshLayout(refresh_layout)
        recycler_view.layoutManager = LinearLayoutManager(mContext)
        msgAdapter.loadMoreModule.setOnLoadMoreListener(this)
        msgAdapter.loadMoreModule.isEnableLoadMore = false
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = msgAdapter
        msgAdapter.setOnItemClickListener(this)
    }

    private fun initListener() {
        refresh_layout.setOnRefreshListener(this)
        btn_login.setOnClickListener(this)
    }

    override fun initData() {
        if (isLogin) {
            refresh_layout.post {
                viewModel.onRefresh(hashMapOf(), isFirst = true)
            }
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
        viewModel.entityLiveData.observe(this, Observer {
            if (it is EyeResult.Success) {
                val entity = it.data
                nextPageUrl = entity.nextPageUrl
                if (isRefresh) {
                    msgAdapter.loadMoreModule.isEnableLoadMore = true
                    msgAdapter.setList(entity.itemList)
                    if (TextUtils.isEmpty(nextPageUrl)) msgAdapter.loadMoreModule.loadMoreEnd()
                } else {
                    msgAdapter.loadMoreModule.isEnableLoadMore = true
                    msgAdapter.addData(entity.itemList)
                    if (TextUtils.isEmpty(nextPageUrl)) {
                        msgAdapter.loadMoreModule.loadMoreEnd()
                    } else {
                        msgAdapter.loadMoreModule.loadMoreComplete()
                    }
                }
                refresh_layout.isEnabled = true
            }
        })
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_login -> {
                ActivityUtils.startActivity(LoginActivity::class.java)
            }
        }
    }

    override fun isUseEventBus(): Boolean = true

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLogin(loginEvent: LoginEvent) {
        if (loginEvent.isLogin) {
            layout_login.visibility = View.GONE
            status_view.showLoadingView()
            isRefresh = true
            viewModel.onRefresh(hashMapOf())
        } else {
            layout_login.visibility = View.VISIBLE
        }
    }

    override fun onRefresh() {
        msgAdapter.loadMoreModule.isEnableLoadMore = false
        isRefresh = true
        viewModel.onRefresh(hashMapOf())
    }

    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            refresh_layout.isEnabled = false
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            viewModel.onLoadMore(map)
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = msgAdapter.getItem(position)
        val actionUrl = item.data.actionUrl
        if (!actionUrl.isNullOrEmpty() && actionUrl.contains("common/")) {
            CategoryActivity.startActivity("发现好友", 3)
        }
    }
}