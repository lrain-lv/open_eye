package com.app.eye.rx

import android.app.Activity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.blankj.utilcode.util.EncodeUtils
import com.just.agentweb.AgentWeb


fun String.getAgentWeb(
    activity: Activity,
    webContent: ViewGroup,
    layoutParams: ViewGroup.LayoutParams = LinearLayout.LayoutParams(-1, -1)
): AgentWeb = AgentWeb.with(activity)//传入Activity or Fragment
    .setAgentWebParent(webContent, layoutParams)//传入AgentWeb 的父控件
    .useDefaultIndicator()
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