package com.app.eye.rx

import android.app.Activity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb


fun String.getAgentWeb(
    activity: Activity,
    webContent: ViewGroup,
    layoutParams: ViewGroup.LayoutParams = LinearLayout.LayoutParams(-1, -1)
): AgentWeb = AgentWeb.with(activity)//传入Activity or Fragment
    .setAgentWebParent(webContent,layoutParams)//传入AgentWeb 的父控件
    .useDefaultIndicator()
    .createAgentWeb()
    .ready()
    .go(this)