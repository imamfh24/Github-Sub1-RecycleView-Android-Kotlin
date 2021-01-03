package com.ifa.githubuser.ui.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ifa.githubuser.R
import com.ifa.githubuser.ui.view.UserDetailFollowersFragment

class UserDetailSectionsPageAdapter(
    private val mContext: Context,
    fm: FragmentManager,
    user: String?
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val userDetail = user

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.followers, R.string.following)

    override fun getCount(): Int = TAB_TITLES.size

    override fun getItem(position: Int): Fragment {
        return UserDetailFollowersFragment.newInstance(position, userDetail)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

}