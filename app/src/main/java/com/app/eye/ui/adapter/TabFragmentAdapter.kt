package com.app.eye.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment

class TabFragmentAdapter(
    var fragmentManager: FragmentManager, var fragments: List<SupportFragment>,
    var titles: List<String>
) :
    FragmentPagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}