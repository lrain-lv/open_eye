package com.app.eye.ui.activity

import TagVideoEntity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.app.eye.R
import com.app.eye.base.BaseMvpActivity
import com.app.eye.ui.adapter.TopicFragmentAdapter
import com.app.eye.ui.fragment.TagDynamicFragment
import com.app.eye.ui.fragment.TagVideoFragment
import com.app.eye.ui.mvp.contract.TagVideoContract
import com.app.eye.ui.mvp.model.entity.TagIndexEntity
import com.app.eye.ui.mvp.presenter.TagVideoPresenter
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_tag_video.*
import me.yokeyword.fragmentation.SupportFragment
import kotlin.math.abs

class TagVideoActivity : BaseMvpActivity<TagVideoContract.Presenter, TagVideoContract.View>(),
    TagVideoContract.View, AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    companion object {
        fun startActivity(id: String, title: String, icon: String, dec: String) {
            val bundle = Bundle().apply {
                putSerializable("id", id)
                putSerializable("title", title)
                putSerializable("icon", icon)
                putSerializable("dec", dec)
            }
            ActivityUtils.startActivity(bundle, TagVideoActivity::class.java)
        }
    }

    private val fragmentList = mutableListOf<SupportFragment>()
    private val titleList = mutableListOf<String>("推荐", "广场")

    private var id: String = ""
    private var title: String = ""
    private var icon: String = ""
    private var dec: String = ""

    override fun getLayoutRes(): Int = R.layout.activity_tag_video

    override fun initView() {
        immersionBar.statusBarDarkFont(false).statusBarColor(R.color.black).init()
        val extras = intent.extras!!
        id = extras.getString("id", "")
        title = extras.getString("title", "")
        icon = extras.getString("icon", "")
        dec = extras.getString("dec", "")
        tv_title.text = title
        tv_title_big.text = title
        tv_dec.text = dec
        Glide.with(mContext)
            .load(icon)
            .centerCrop()
            .into(iv_bg)
        tool_bar.setNavigationOnClickListener {
            onBackPressedSupport()
        }
        iv_back.setOnClickListener(this)
        iv_publish.setOnClickListener(this)
        fragmentList.apply {
            add(TagVideoFragment.newInstance(id))
            add(TagDynamicFragment.newInstance(id))
        }
        val topicFragmentAdapter =
            TopicFragmentAdapter(supportFragmentManager, fragmentList, titleList)
        view_pager.apply {
            offscreenPageLimit = fragmentList.size
            adapter = topicFragmentAdapter
            currentItem = 0
        }
        tab_layout.apply {
            setViewPager(view_pager)
            currentTab = 0
        }
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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

        appbar_layout.addOnOffsetChangedListener(this)
    }

    override fun initData() {
        tv_follow_count.post {
            mPresenter?.getTagIndexRequest(id)
        }
    }

    override fun reConnect() {
    }

    override fun createPresenter(): TagVideoContract.Presenter? = TagVideoPresenter()

    override fun setTagIndexResponse(entity: TagIndexEntity?) {
        entity ?: onBackPressedSupport()
        tv_follow_count.text =
            "${entity!!.tagInfo.tagFollowCount}人关注 / ${entity!!.tagInfo.lookCount}人参与"
        tv_title_big.text = entity.tagInfo.name
        tv_title.text = entity.tagInfo.name
        tv_dec.text = entity.tagInfo.description
        Glide.with(mContext)
            .load(entity.tagInfo.bgPicture)
            .centerCrop()
            .into(iv_bg)
    }

    override fun setTagVideoResponse(entity: TagVideoEntity?) {

    }

    override fun setTagDynamicResponse(entity: TagVideoEntity?) {
    }

    override fun hideLoading() {
    }

    override fun onDestroy() {
        appbar_layout.removeOnOffsetChangedListener(this)
        super.onDestroy()
    }

    override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
        tool_bar.alpha = 1.toFloat() * (abs(p1 * 1.0f) / p0!!.totalScrollRange.toFloat())
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                onBackPressedSupport()
            }
            R.id.iv_publish -> {
                ToastUtils.setBgColor(Color.RED)
                ToastUtils.showShort("该功能暂未开放！")
            }
        }
    }
}