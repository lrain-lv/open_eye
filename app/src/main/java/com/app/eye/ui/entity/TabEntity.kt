package com.app.eye.ui.entity

import com.flyco.tablayout.listener.CustomTabEntity

class TabEntity constructor(
    private val title: String,
    private val unSelect: Int,
    private val selected: Int) :
    CustomTabEntity {
    override fun getTabUnselectedIcon(): Int = unSelect


    override fun getTabSelectedIcon(): Int = selected

    override fun getTabTitle(): String = title
}