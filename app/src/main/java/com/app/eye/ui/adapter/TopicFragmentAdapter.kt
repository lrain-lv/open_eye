package com.app.eye.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import me.yokeyword.fragmentation.SupportFragment

class TopicFragmentAdapter(
    var fm: FragmentManager,
    var fragments: MutableList<SupportFragment>,
    var titles: MutableList<String>
) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}