package com.app.eye.rx

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.LinearLayout
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.ui.entity.BrandApiRequest
import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SizeUtils
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebViewClient
import com.orhanobut.logger.Logger

fun Float.dp2px(): Float {
    return SizeUtils.dp2px(this).toFloat()
}

fun String.getAgentWeb(
    activity: Activity,
    webContent: ViewGroup,
    layoutParams: ViewGroup.LayoutParams = LinearLayout.LayoutParams(-1, -1)
): AgentWeb = AgentWeb.with(activity)//传入Activity or Fragment
    .setAgentWebParent(webContent, layoutParams)//传入AgentWeb 的父控件
    .useDefaultIndicator()
    .setWebViewClient(object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            Logger.e(request!!.url.toString())
            return super.shouldOverrideUrlLoading(view, request)
        }
    })
    .createAgentWeb()
    .ready()
    .go(this)

fun String.urlToMap(): Map<String, String> {
    val map = hashMapOf<String, String>()
    val index = this.indexOf("?")
    val result = this.substring(index + 1)
    val split = result.split("&")
    split.forEach {
        map[it.split("=")[0]] = it.split("=")[1]
    }
    return map
}

fun String.actionUrlToMap(): Map<String, String> {
    val indexOf = this.indexOf("?")
    val substring = this.substring(indexOf + 1)
    return substring.formToMap()
}

fun String.formToMap(): Map<String, String> {
    val map = hashMapOf<String, String>()
    val split = this.split("&")
    split.forEach {
        val urlDecode = EncodeUtils.urlDecode(it)
        val index = urlDecode.indexOf("=")
        map[urlDecode.substring(0, index)] = urlDecode.substring(index + 1)
    }
    return map
}

fun String.actionUrlToRequest(): BrandApiRequest {
    val urlDecode = EncodeUtils.urlDecode(this)
    val index = urlDecode.indexOf("{")
    val indexLast = urlDecode.lastIndexOf("}")
    val substring = urlDecode.substring(index, indexLast + 1)
    return GsonUtils.fromJson(substring, BrandApiRequest::class.java)
}

fun setOnClickListener(vararg v: View?, block: View.() -> Unit) {
    v.forEach { it?.setOnClickListener(block) }
}

fun <T : Any> EyeResult<T>.checkSuccess(
    onSuccess: (T) -> Unit,
    onError: ((String?) -> Unit)? = null
) {
    if (this is EyeResult.Success) {
        onSuccess(this.data)
    } else {
        onError?.let { it }
    }
}

fun <T : Any> EyeResult<T>.isSuccess(): Boolean {
    return this is EyeResult.Success
}