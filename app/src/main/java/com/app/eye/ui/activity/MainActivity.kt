package com.app.eye.ui.activity

import android.provider.Settings
import com.app.eye.R
import com.app.eye.base.BaseActivity
import com.app.eye.event.ChangeTabEvent
import com.app.eye.ui.fragment.*
import com.app.eye.ui.entity.TabEntity
import com.app.eye.utils.DataStoreUtils
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.reflect.Array.setInt

class MainActivity : BaseActivity() {
    override fun getLayoutRes(): Int = R.layout.activity_main

    private var homeFragment: HomeFragment? = null
    private var communityFragment: CommunityFragment? = null
    private var emptyFragment: EmptyFragment? = null
    private var notificationFragment: NotificationFragment? = null
    private var mineFragment: MineFragment? = null

    private var currentFragment: ISupportFragment? = null

    private var tabEntityList = arrayListOf<CustomTabEntity>()

    private var fragments = arrayOfNulls<SupportFragment>(5)

    private var firstPressTime = 0L

    private val selectedRes =
        listOf<Int>(
            R.mipmap.ic_tab_strip_icon_feed_selected,
            R.mipmap.ic_tab_strip_icon_follow_selected,
            R.mipmap.ic_1px_empty,
            R.mipmap.ic_tab_strip_icon_category_selected,
            R.mipmap.ic_tab_strip_icon_profile_selected
        )

    private val unSelectedRes = listOf<Int>(
        R.mipmap.ic_tab_strip_icon_feed,
        R.mipmap.ic_tab_strip_icon_follow,
        R.mipmap.ic_1px_empty,
        R.mipmap.ic_tab_strip_icon_category,
        R.mipmap.ic_tab_strip_icon_profile
    )

    private var title = listOf("首页", "社区", "", "通知", "我的")

    private val mainScope = MainScope()

    override fun initView() {
        initStatusBar()
        iv_add.setOnClickListener {
            mainScope.launch {
                val isLogin = DataStoreUtils.getInstance().readBooleanData("isLogin")
                if (!isLogin) {
                    ActivityUtils.startActivity(LoginActivity::class.java)
                }
            }
        }
        val firstFragment = findFragment(HomeFragment::class.java)
        if (firstFragment == null) {
            initFragment()
            loadMultipleRootFragment(R.id.container, 0, *fragments)
        } else {
            fragments[0] = firstFragment
            fragments[1] = findFragment(CommunityFragment::class.java)
            fragments[2] = findFragment(EmptyFragment::class.java)
            fragments[3] = findFragment(NotificationFragment::class.java)
            fragments[4] = findFragment(MineFragment::class.java)
        }
        currentFragment = fragments[0]
        initTab()
    }

    private fun initStatusBar() {
        immersionBar.statusBarDarkFont(true)
            .init()
    }

    private fun initTab() {
        for (index in 0..4) {
            val tabEntity = TabEntity(title[index], unSelectedRes[index], selectedRes[index])
            tabEntityList.add(tabEntity)
        }
        bottom_bar.setTabData(tabEntityList)
        bottom_bar.currentTab = 0
        bottom_bar.getTitleView(0).paint.isFakeBoldText = true
        bottom_bar.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                bottom_bar.currentTab = position
                showHideFragment(fragments[position], currentFragment)
                currentFragment = fragments[position]
            }

            override fun onTabReselect(position: Int) {
            }

        })
    }

    private fun initFragment() {
        homeFragment = HomeFragment.newInstance()
        communityFragment = CommunityFragment.newInstance()
        emptyFragment = EmptyFragment.newInstance()
        notificationFragment = NotificationFragment.newInstance()
        mineFragment = MineFragment.newInstance()
        fragments[0] = homeFragment!!
        fragments[1] = communityFragment!!
        fragments[2] = emptyFragment!!
        fragments[3] = notificationFragment!!
        fragments[4] = mineFragment!!
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onChangeIndexEvent(event: ChangeTabEvent) {
        bottom_bar.currentTab = 0
        showHideFragment(fragments[0], currentFragment)
        currentFragment = fragments[0]
    }

    override fun initData() {
    }

    override fun reConnect() {
    }

    override fun isUseEventBus(): Boolean = true

    override fun onBackPressedSupport() {

        if (System.currentTimeMillis() - firstPressTime < 2000) {
            super.onBackPressedSupport()
            ActivityUtils.finishAllActivities()
            AppUtils.exitApp()
        } else {
            ToastUtils.showShort("再按一次退出")
            firstPressTime = System.currentTimeMillis()
        }
    }
}