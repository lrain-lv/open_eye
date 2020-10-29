package com.app.eye.ui.activity

import android.graphics.Typeface
import androidx.viewpager.widget.ViewPager
import com.app.eye.R
import com.app.eye.base.BaseActivity
import com.app.eye.ui.adapter.TabFragmentAdapter
import com.app.eye.ui.fragment.RankListFragment
import kotlinx.android.synthetic.main.activity_rank_avtivity.*
import me.yokeyword.fragmentation.SupportFragment

class RankActivity : BaseActivity() {

    override fun getLayoutRes(): Int = R.layout.activity_rank_avtivity

    private lateinit var tabFragmentAdapter: TabFragmentAdapter

    private val titles = mutableListOf("周排行", "月排行", "总排行")
    private var fragments = mutableListOf<SupportFragment>()

    override fun initView() {
        immersionBar.statusBarDarkFont(true).fitsSystemWindows(true).init()
        initToolBar(tool_bar)
        val typeface = Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf")
        tv_title.typeface = typeface

        fragments.apply {
            add(RankListFragment.newInstance(0))
            add(RankListFragment.newInstance(1))
            add(RankListFragment.newInstance(2))
        }
        tabFragmentAdapter = TabFragmentAdapter(supportFragmentManager, fragments, titles)
        view_pager.apply {
            offscreenPageLimit = titles.size
            adapter = tabFragmentAdapter
            currentItem = 0
        }
        tab_layout.apply {
            setViewPager(view_pager)
            currentTab = 0
            getTitleView(0).paint.isFakeBoldText = true
        }

        view_pager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
            override fun onPageSelected(position: Int) {
                tab_layout.currentTab = position
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    override fun initData() {
    }

    override fun reConnect() {
    }
}