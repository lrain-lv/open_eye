package com.app.eye.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.eye.R
import com.app.eye.base.mvvm.BaseVMActivity
import com.app.eye.event.LoginEvent
import com.app.eye.http.Constant
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.ui.mvp.contract.LoginContract
import com.app.eye.ui.mvp.presenter.LoginPresenter
import com.app.eye.ui.mvvm.factory.InjectorUtil
import com.app.eye.ui.mvvm.viewmodel.LoginViewModel
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus

class LoginActivity : BaseVMActivity(),
    View.OnClickListener {

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(
            this,
            InjectorUtil.getLoginVMFactory()
        ).get(LoginViewModel::class.java)
    }

    override fun startObserver() {
        viewModel.loginEntity.observe(this, Observer {
            if (it is EyeResult.Success) {
                val entity = it.data
                when (entity.error) {
                    0 -> {
                        EventBus.getDefault().post(LoginEvent(loginEntity = entity))
                        onBackPressedSupport()
                    }
                    else -> {
                        ToastUtils.showShort(entity.msg ?: "请求失败")
                    }
                }
            }
        })
    }

    override fun getLayoutRes(): Int = R.layout.activity_login

    override fun initView() {
        immersionBar.statusBarDarkFont(false)
            .init()
        tv_agreement.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        iv_close.setOnClickListener(this)
        et_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val name = s.toString().trim()
                val pwd = et_pwd.text.toString().trim()
                btn_login.isEnabled = name.isNotEmpty() && pwd.isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        et_pwd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val name = et_name.text.toString().trim()
                val pwd = s.toString().trim()
                btn_login.isEnabled = name.isNotEmpty() && pwd.isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

    }

    override fun initData() {
    }

    override fun reConnect() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_agreement -> {
                WebActivity.startWebActivity(url = Constant.USER_AGREEMENT, title = "\"开眼\"软件用户协议")
            }
            R.id.iv_close -> {
                onBackPressedSupport()
            }
            R.id.btn_login -> {
                viewModel.doLogin(
                    mapOf(
                        "username" to et_name.text.toString().trim(),
                        "pwd" to et_pwd.text.toString().toString()
                    )
                )
            }
        }
    }

}