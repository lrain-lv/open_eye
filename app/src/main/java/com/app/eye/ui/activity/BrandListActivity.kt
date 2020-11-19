package com.app.eye.ui.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.eye.R
import com.app.eye.base.BaseMvpActivity
import com.app.eye.rx.actionUrlToRequest
import com.app.eye.ui.adapter.BrandListAdapter
import com.app.eye.ui.mvp.contract.BrandWallContract
import com.app.eye.ui.mvp.model.entity.BrandListItemX
import com.app.eye.ui.mvp.model.entity.BrandWallEntity
import com.app.eye.ui.mvp.presenter.BrandWallPresenter
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import kotlinx.android.synthetic.main.activity_brand_list.*

class BrandListActivity : BaseMvpActivity<BrandWallContract.Presenter, BrandWallContract.View>(),
    BrandWallContract.View {

    val listAdapter = BrandListAdapter(mutableListOf())

    override fun getLayoutRes(): Int = R.layout.activity_brand_list

    override fun initView() {
        immersionBar.fitsSystemWindows(true)
            .statusBarDarkFont(true)
            .statusBarColor(R.color.white)
            .init()
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = listAdapter
        tool_bar.setNavigationOnClickListener { onBackPressedSupport() }
        listAdapter.setOnItemClickListener { adapter, view, position ->
            val item = listAdapter.getItem(position)
            if (item.itemType == 1) {
                val name = item.name
                val link = item.link
                BrandDetailActivity.startActivity(link!!.actionUrlToRequest().params.resource_id.toString())
            }
        }
    }

    override fun initData() {
        recycler_view.post {
            mPresenter?.getListRequest()
        }
    }

    override fun createPresenter(): BrandWallContract.Presenter? = BrandWallPresenter()

    override fun setBrandWallData(entity: BrandWallEntity?) {
    }

    override fun setBrandListData(
        list: MutableList<BrandListItemX>,
        indexList: MutableList<String>
    ) {
        listAdapter.setList(list)
        side_bar.setIndexItems(*indexList.toTypedArray())
        side_bar.setOnSelectIndexItemListener {
            list.forEachIndexed { index, brandListItemX ->
                if (StringUtils.equalsIgnoreCase(it, brandListItemX.name)) {
                    ((recycler_view.layoutManager) as LinearLayoutManager).scrollToPositionWithOffset(
                        index,
                        0
                    )
                }
            }
        }
    }

    override fun hideLoading() {
        super.hideLoading()
        status_view.showContentView()
    }
}