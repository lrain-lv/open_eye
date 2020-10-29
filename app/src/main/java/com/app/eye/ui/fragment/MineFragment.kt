package com.app.eye.ui.fragment

import android.view.View
import com.app.eye.R
import com.app.eye.base.BaseFragment
import com.app.eye.event.LoginEvent
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.activity.LoginActivity
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.Glide
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_mine.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MineFragment : BaseFragment(), View.OnClickListener {


    override fun getLayoutRes(): Int = R.layout.fragment_mine

    private lateinit var avatar: String
    private lateinit var nick: String

    override fun initView() {
        initListener()
        Observable.just(SPUtils.getInstance("eye"))
            .map {
                avatar = it.getString("avatar", "")
                nick = it.getString("nick", "")
                ""
            }
            .compose(SchedulerUtils.ioToMain())
            .subscribe {
                Glide.with(mContext)
                    .load(if (avatar.isNotEmpty()) avatar else R.mipmap.pgc_default_avatar)
                    .circleCrop()
                    .into(iv_header)
                tv_login.text = if (nick.isNotEmpty()) nick else "点击登录即可评论及发布内容"
            }
        tv_version.text = "Version ${AppUtils.getAppVersionName()}"
    }

    private fun initListener() {
        iv_header.setOnClickListener(this)
        iv_more.setOnClickListener(this)
        tv_login.setOnClickListener(this)
        layout_collect.setOnClickListener(this)
        layout_cache.setOnClickListener(this)
        tv_attention.setOnClickListener(this)
        tv_record.setOnClickListener(this)
        tv_notification.setOnClickListener(this)
        tv_badge.setOnClickListener(this)
        tv_feedback.setOnClickListener(this)
        tv_submit.setOnClickListener(this)
    }

    override fun initData() {
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MineFragment().apply {
            }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(loginEvent: LoginEvent) {
        val loginEntity = loginEvent.loginEntity
        avatar = loginEntity.member?.avatar!!
        nick = loginEntity.member.nick
        Glide.with(mContext)
            .load(loginEntity.member?.avatar)
            .circleCrop()
            .into(iv_header)
        tv_login.text = loginEntity.member?.nick
        Observable.just(loginEntity)
            .observeOn(Schedulers.io())
            .subscribe {
                SPUtils.getInstance("eye").put("isLogin", true)
                SPUtils.getInstance("eye")
                    .put("avatar", loginEntity.member?.avatar)
                SPUtils.getInstance("eye")
                    .put("nick", loginEntity.member?.nick)
            }
    }


    override fun reConnect() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_header, R.id.tv_login -> {
                if (avatar.isNotEmpty()) return
                ActivityUtils.startActivity(LoginActivity::class.java)
            }
        }
    }

    override fun isUseEventBus(): Boolean = true
    override fun useLazyLoad(): Boolean = true
}