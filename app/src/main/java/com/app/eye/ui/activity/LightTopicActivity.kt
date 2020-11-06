package com.app.eye.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
import com.app.eye.R
import com.app.eye.base.BaseMvpActivity
import com.app.eye.rx.loadImageCommon
import com.app.eye.ui.adapter.LightTopicAdapter
import com.app.eye.ui.mvp.contract.LightTopicContract
import com.app.eye.ui.mvp.model.entity.LightTopicInternalEntity
import com.app.eye.ui.mvp.presenter.LightTopicPresenter
import com.app.eye.widgets.videoplayer.AutoPlayScrollListener
import com.app.eye.widgets.videoplayer.Jzvd
import com.blankj.utilcode.util.ActivityUtils
import kotlinx.android.synthetic.main.activity_light_topic_internal.*

class LightTopicActivity : BaseMvpActivity<LightTopicContract.Presenter, LightTopicContract.View>(),
    LightTopicContract.View {

    companion object {
        fun startActivity(title: String, id: Int) {
            val bundle = Bundle().apply {
                putString("title", title)
                putInt("id", id)
            }
            ActivityUtils.startActivity(bundle, LightTopicActivity::class.java)
        }
    }

    private val title: String by lazy { intent.extras?.getString("title", "")!! }

    private val id: Int by lazy { intent.extras?.getInt("id", -1)!! }

    private lateinit var headerView: View
    private lateinit var imgBg: ImageView
    private lateinit var tvTitleSm: TextView
    private lateinit var tvDec: TextView
    private var adapter = LightTopicAdapter(mutableListOf())

    override fun getLayoutRes(): Int = R.layout.activity_light_topic_internal

    override fun initView() {
        immersionBar.fitsSystemWindows(true)
            .statusBarDarkFont(true).init()
        if (title.isEmpty() || id == -1) onBackPressedSupport()
        initToolBar(tool_bar)
        tv_title.text = title
        val manager = LinearLayoutManager(mContext)
        recycler_view.layoutManager = manager
        initHeaderView()
        adapter.addHeaderView(headerView)
        adapter.loadMoreModule.setOnLoadMoreListener {}
        adapter.loadMoreModule.isEnableLoadMore = false
        adapter.setOnItemClickListener { _, view, position ->
            val item = adapter.getItem(position)
            VideoDetailActivity.startActivity(item.data.content.data.id.toString())
        }
        recycler_view.adapter = adapter
        recycler_view.addOnChildAttachStateChangeListener(object :
            OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {}
            override fun onChildViewDetachedFromWindow(view: View) {
                val jzvd: Jzvd? = view.findViewById(R.id.jzvd)
                if (jzvd != null && Jzvd.CURRENT_JZVD != null && jzvd.jzDataSource.containsTheUrl(
                        Jzvd.CURRENT_JZVD.jzDataSource.currentUrl)
                ) {
                    if (Jzvd.CURRENT_JZVD != null && (Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN)) {
                        Jzvd.releaseAllVideos()
                    }
                }
            }
        })
        recycler_view.addOnScrollListener(AutoPlayScrollListener())
    }

    private fun initHeaderView() {
        headerView = layoutInflater.inflate(R.layout.layout_light_topic_header,
            recycler_view,
            false)
        imgBg = headerView.findViewById<ImageView>(R.id.iv_bg)
        tvTitleSm = headerView.findViewById<TextView>(R.id.tv_title_sm)
        tvDec = headerView.findViewById<TextView>(R.id.tv_dec)
    }

    override fun initData() {
        recycler_view.post {
            mPresenter?.getLightTopicRequest(id)
        }
    }

    override fun onBackPressedSupport() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressedSupport()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos();
    }

    override fun reConnect() {
    }

    override fun createPresenter(): LightTopicContract.Presenter? = LightTopicPresenter()

    override fun setLightTopicResponse(entity: LightTopicInternalEntity?) {
        entity ?: status_view.showEmptyView()
        imgBg.loadImageCommon(mContext, entity?.headerImage)
        tvDec.text = entity?.text
        tvTitleSm.text = entity?.brief
        adapter.setList(entity?.itemList)
        adapter.loadMoreModule.isEnableLoadMore = true
        adapter.loadMoreModule.loadMoreEnd()
    }

    override fun hideLoading() {
        status_view.showContentView()
    }

}