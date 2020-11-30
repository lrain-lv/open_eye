package com.app.eye.ui.fragment

import android.view.View
import com.app.eye.R
import com.app.eye.base.BaseFragment
import com.app.eye.event.LoginEvent
import com.app.eye.rx.SchedulerUtils
import com.app.eye.rx.loadImageCircle
import com.app.eye.rx.setOnClickListener
import com.app.eye.ui.activity.BadgeActivity
import com.app.eye.ui.activity.LoginActivity
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext

class MineFragment : BaseFragment() {


    override fun getLayoutRes(): Int = R.layout.fragment_mine

    private lateinit var avatar: String
    private lateinit var nick: String

    private val spUtils: SPUtils by lazy { SPUtils.getInstance("eye") }

    override fun initView() {
        initListener()
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                avatar = spUtils.getString("avatar", "")
                nick = spUtils.getString("nick", "")
            }
            Glide.with(mContext)
                .load(avatar)
                .error(R.mipmap.pgc_default_avatar)
                .circleCrop()
                .override(SizeUtils.dp2px(80f), SizeUtils.dp2px(80f))
                .into(iv_header)
            tv_login.text = if (nick.isNotEmpty()) nick else "点击登录即可评论及发布内容"
        }

        tv_version.text = "Version ${AppUtils.getAppVersionName()}"
    }

    private fun initListener() {
        setOnClickListener(
            iv_header,
            iv_more,
            tv_login,
            layout_collect,
            layout_cache,
            tv_attention,
            tv_record, tv_notification, tv_badge, tv_feedback, tv_submit
        ) {
            when (this.id) {
                R.id.iv_header, R.id.tv_login -> {
                    if (avatar.isEmpty())
                        ActivityUtils.startActivity(LoginActivity::class.java)
                }
                R.id.tv_badge -> {
                    ActivityUtils.startActivity(BadgeActivity::class.java)
                }
            }
        }
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
        iv_header.loadImageCircle(mContext, loginEntity.member.avatar, 80f)
        tv_login.text = loginEntity.member.nick
        Logger.e(loginEntity.member.avatar + loginEntity.member.nick)
        GlobalScope.launch(Dispatchers.IO) {
            spUtils.put("isLogin", true)
            spUtils.put("avatar", loginEntity.member.avatar)
            spUtils.put("nick", loginEntity.member.nick)
        }
    }


    override fun reConnect() {
    }


    override fun isUseEventBus(): Boolean = true
    override fun useLazyLoad(): Boolean = true
}