package com.app.eye.ui.fragment

import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseMvpFragment
import com.app.eye.event.LoginEvent
import com.app.eye.rx.urlToMap
import com.app.eye.ui.activity.CategoryActivity
import com.app.eye.ui.activity.LoginActivity
import com.app.eye.ui.adapter.PrivateMsgAdapter
import com.app.eye.ui.mvp.contract.PushContract
import com.app.eye.ui.mvp.model.entity.MessageEntity
import com.app.eye.ui.mvp.model.entity.PushEntity
import com.app.eye.ui.mvp.model.entity.RecFriendEntity
import com.app.eye.ui.mvp.presenter.PushPresenter
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SPUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_message.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MessageFragment : BaseMvpFragment<PushContract.Presenter, PushContract.View>(),
    PushContract.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
    OnLoadMoreListener, OnItemClickListener {

    companion object {
        @JvmStatic
        fun newInstance() =
            MessageFragment().apply {
            }
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
                mPresenter?.getPrivateMsgRequest(hashMapOf())
            }
        }
    }


    override fun createPresenter(): PushContract.Presenter? = PushPresenter()

    override fun setPushResponse(pushEntity: PushEntity?) {
    }

    override fun setPrivateMsgResponse(entity: MessageEntity?) {
        entity ?: status_view.showEmptyView()
        nextPageUrl = entity?.nextPageUrl
        if (isRefresh) {
            msgAdapter.loadMoreModule.isEnableLoadMore = true
            msgAdapter.setList(entity?.itemList)
            if (TextUtils.isEmpty(nextPageUrl)) msgAdapter.loadMoreModule.loadMoreEnd()
        } else {
            msgAdapter.loadMoreModule.isEnableLoadMore = true
            msgAdapter.addData(entity?.itemList!!)
            if (TextUtils.isEmpty(nextPageUrl)) {
                msgAdapter.loadMoreModule.loadMoreEnd()
            } else {
                msgAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        refresh_layout.isEnabled = true
    }

    override fun setRecFriendResponse(entity: RecFriendEntity?) {


    }

    override fun hideLoading() {
        refresh_layout.isRefreshing = false
        status_view.showContentView()
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
            mPresenter?.getPrivateMsgRequest(hashMapOf())
        } else {
            layout_login.visibility = View.VISIBLE
        }
    }

    override fun onRefresh() {
        msgAdapter.loadMoreModule.isEnableLoadMore = false
        isRefresh = true
        mPresenter?.getPrivateMsgRequest(hashMapOf())
    }

    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            refresh_layout.isEnabled = false
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            mPresenter?.getPrivateMsgRequest(map)
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