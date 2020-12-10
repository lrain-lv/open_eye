package com.app.eye.ui.fragment

import androidx.core.view.doOnLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import coil.load
import coil.transform.CircleCropTransformation
import com.app.eye.R
import com.app.eye.base.BaseFragment
import com.app.eye.event.LoginEvent
import com.app.eye.rx.dp2px
import com.app.eye.rx.loadImageCircle
import com.app.eye.rx.setOnClickListener
import com.app.eye.ui.activity.BadgeActivity
import com.app.eye.ui.activity.LoginActivity
import com.app.eye.utils.DataStoreUtils
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MineFragment : BaseFragment() {


    override fun getLayoutRes(): Int = R.layout.fragment_mine

    private lateinit var avatar: String
    private lateinit var nick: String

    private val mainScope = MainScope()

    override fun initView() {
        requireActivity().classLoader
        initListener()
        mainScope.launch {
            avatar =
                DataStoreUtils.getInstance().readStringFlow("avatar", "").first().toString()
            nick = DataStoreUtils.getInstance().readStringFlow("nick", "点击登录即可评论及发布内容").first()
                .toString()
            iv_header.load(avatar) {
                error(R.mipmap.pgc_default_avatar)
                size(80f.dp2px().toInt())
                transformations(CircleCropTransformation())
            }
            tv_login.text = nick
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
            tv_record, tv_notification, tv_badge, tv_feedback, tv_submit, tv_logout
        ) {
            when (this.id) {
                R.id.iv_header, R.id.tv_login -> {
                    if (avatar.isEmpty())
                        ActivityUtils.startActivity(LoginActivity::class.java)
                }
                R.id.tv_badge -> {
                    ActivityUtils.startActivity(BadgeActivity::class.java)
                }

                R.id.tv_logout -> {
                    mainScope.launch {
                        withContext(Dispatchers.IO) {
                            DataStoreUtils.getInstance().saveBooleanData("isLogin", false)
                            DataStoreUtils.getInstance().saveStringData("avatar", "")
                            DataStoreUtils.getInstance().saveStringData("nick", "点击登录即可评论及发布内容")
                        }
                        iv_header.load(R.mipmap.pgc_default_avatar) {
                            size(80f.dp2px().toInt())
                            transformations(CircleCropTransformation())
                        }
                        tv_login.text = "点击登录即可评论及发布内容"
                    }
                }
            }
        }
    }

    override fun initData() {
    }

    companion object {
        @JvmStatic
        fun newInstance() = MineFragment()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(loginEvent: LoginEvent) {
        val loginEntity = loginEvent.loginEntity
        avatar = loginEntity.member?.avatar!!
        nick = loginEntity.member.nick
        iv_header.loadImageCircle(loginEntity.member.avatar, 80f)
        tv_login.text = loginEntity.member.nick
        Logger.e(loginEntity.member.avatar + loginEntity.member.nick)
        GlobalScope.launch(Dispatchers.IO) {
            DataStoreUtils.getInstance().saveBooleanData("isLogin", true)
            DataStoreUtils.getInstance().saveStringData("avatar", loginEntity.member.avatar)
            DataStoreUtils.getInstance().saveStringData("nick", loginEntity.member.nick)
        }
    }

    override fun reConnect() {
    }


    override fun isUseEventBus(): Boolean = true
    override fun useLazyLoad(): Boolean = true
}