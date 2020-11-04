package com.app.eye.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.viewpager2.widget.ViewPager2
import com.app.eye.R
import com.app.eye.base.BaseMvpActivity
import com.app.eye.event.callback.OnItemClickCallback
import com.app.eye.rx.urlToMap
import com.app.eye.ui.adapter.GalleryAdapter
import com.app.eye.ui.mvp.contract.CommunityContract
import com.app.eye.ui.mvp.model.entity.ComAttentionEntity
import com.app.eye.ui.mvp.model.entity.ComRecEntity
import com.app.eye.ui.mvp.presenter.CommunityPresenter
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_gallery.*


class GalleryActivity : BaseMvpActivity<CommunityContract.Presenter, CommunityContract.View>(),
    View.OnClickListener,
    OnItemClickCallback, CommunityContract.View {

    companion object {
        fun startGalleryActivity(data: ComRecEntity, pos: Int) {
            val bundle = Bundle().apply {
                putSerializable("data", data)
                putInt("pos", pos)
            }
            ActivityUtils.startActivity(bundle,
                GalleryActivity::class.java,
                R.anim.in_from_bottom,
                R.anim.top_slient)
        }
    }

    private lateinit var data: ComRecEntity
    private lateinit var galleryAdapter: GalleryAdapter
    private var pos: Int = 0

    private lateinit var animatorSet: AnimatorSet

    override fun getLayoutRes(): Int = R.layout.activity_gallery

    private var currentPos = 0
    private var defaultIndex = 0

    private var isPageSelected = false

    override fun initView() {
        val extras = intent.extras!!
        data = extras.getSerializable("data") as ComRecEntity
        pos = extras.getInt("pos")
        data.itemList = data.itemList.subList(pos, data.itemList.size)
        immersionBar
            .statusBarDarkFont(false)
            .statusBarColor(R.color.black, 0f)
            .init()
        initAnim()
        initListener()
        galleryAdapter = GalleryAdapter(data.itemList, this)

        view_pager2.apply {
            adapter = galleryAdapter
            offscreenPageLimit = 1
            orientation = ViewPager2.ORIENTATION_VERTICAL
            currentItem = 0
        }
        galleryAdapter.setOnItemClickListener { adapter, view, position -> ToastUtils.showShort("click") }
        tv_content.movementMethod = object : ScrollingMovementMethod() {}
        view_pager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                Logger.e("ddd--onPageScrollStateChanged")
                if (state == ViewPager2.SCROLL_STATE_IDLE && isPageSelected) {
                    Glide.with(this@GalleryActivity).resumeRequests()
                    updateView()
                    startAnim()
                    isPageSelected = false
                    if (data.itemList.size - currentPos <= 2) {
                        val map = data.nextPageUrl?.urlToMap() as HashMap<String, String>
                        mPresenter?.getRecRequest(false, map)
                    }
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
                stopAnim()
                Glide.with(this@GalleryActivity).pauseRequests()
                Logger.e("ddd--onPageScrolled")
                if (defaultIndex == position && positionOffsetPixels == 0) {
                    //进入页面后，调用setCurrentItem函数，手动触发onPageScrollStateChanged函数。
                    onPageScrollStateChanged(ViewPager2.SCROLL_STATE_IDLE)
                    defaultIndex = -1
                }
            }

            override fun onPageSelected(position: Int) {
                Logger.e("ddd--onPageSelected$position")
                isPageSelected = true
                currentPos = position
            }
        })
    }

    private fun updateView() {
        val comItem = data.itemList[currentPos]
        Glide.with(this)
            .load(comItem.data.header.icon)
            .circleCrop()
            .into(iv_header)
        tv_name.text = comItem.data.header.issuerName.trim()
        tv_content.text = comItem.data.content.data.description?.trim()
        tv_like_count.text = comItem.data.content.data.consumption.collectionCount.toString()
        tv_comment_count.text = comItem.data.content.data.consumption.replyCount.toString()
    }

    private fun initAnim() {
        val ivAnim = ObjectAnimator.ofFloat(iv_close, "alpha", 0f, 1f)
        val layoutAnim = ObjectAnimator.ofFloat(layout, "alpha", 0f, 1f)

        animatorSet = AnimatorSet()
        animatorSet.playTogether(ivAnim, layoutAnim)
        animatorSet.duration = 600
        animatorSet.interpolator = DecelerateInterpolator()
    }

    private fun startAnim() {
        animatorSet.start()
    }

    private fun stopAnim() {
        animatorSet.cancel()
    }

    private fun initListener() {
        iv_close.setOnClickListener(this)
    }

    override fun initData() {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_close -> {
                onBackPressedSupport()
            }
        }
    }

    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
        overridePendingTransition(R.anim.top_slient, R.anim.out_from_bottom)
    }


    override fun onClick() {

    }

    override fun createPresenter(): CommunityContract.Presenter? = CommunityPresenter()

    override fun setComRecResponse(comRecEntity: ComRecEntity?) {
        galleryAdapter.addData(comRecEntity?.itemList!!)
        data.nextPageUrl = comRecEntity.nextPageUrl
    }

    override fun setComAttentionResponse(entity: ComAttentionEntity?) {
    }

}