package com.group.githubfinder.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.group.githubfinder.ui.FollowFragment

class SectionPageAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = activity.intent.getStringExtra("username") ?: "default_value"
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.ARG_POSITION, position)
            putString(FollowFragment.ARG_USERNAME, username)
        }
        return fragment
    }
}