package com.koleychik.currencyvalue.ui.viewPager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.koleychik.currencyvalue.R
import com.koleychik.currencyvalue.additions.Keys
import com.koleychik.currencyvalue.ui.screens.ConvertFragment
import com.koleychik.currencyvalue.ui.screens.FavoriteFragment
import kotlinx.android.synthetic.main.fragment_view_pager.view.*

class ViewPagerFragment : Fragment() {

    private lateinit var root: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_view_pager, container, false)

        Log.d(Keys.APP,"Start View Pager")

        makeViewPager()
        makeBottomNavBar()

        return root
    }

    private fun makeViewPager() {
        val adapter = ViewPagerAdapter(
            list = getListFragment(),
            fm = requireActivity().supportFragmentManager,
            lifecycle = lifecycle
        )

        root.viewPager.adapter = adapter

        root.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> root.bottomNavBar.menu.findItem(R.id.convertFragment).isChecked = true
                    1 -> root.bottomNavBar.menu.findItem(R.id.favoriteFragment).isChecked = true
                }
            }
        })
    }

    private fun getListFragment() = mutableListOf(ConvertFragment(), FavoriteFragment())

    private fun makeBottomNavBar() {
        root.bottomNavBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.convertFragment -> {
                    root.viewPager.currentItem = 0
                    true
                }
                R.id.favoriteFragment -> {
                    root.viewPager.currentItem = 1
                    true
                }
                else -> false
            }
        }
    }
}