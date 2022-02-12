package com.homework.cryptomessenger.presentation

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.homework.cryptomessenger.R
import com.homework.cryptomessenger.presentation.utils.CustomFragmentFactory
import com.homework.cryptomessenger.presentation.utils.FragmentTag
import com.homework.cryptomessenger.presentation.utils.MenuItemIdx

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fragmentFactory = CustomFragmentFactory.create(FragmentTag.AUTH_FRAGMENT_TAG)
            addFragment(fragmentFactory)
        }
        initNavigationListener()
    }

    private fun initNavigationListener() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar)
        bottomNavigationView.setOnItemSelectedListener {
            actionBottom(it)
        }
    }

    /**
     * Bottom navigation listener action
     * @param item new selected item
     */
    private fun actionBottom(item: MenuItem): Boolean {
        val tag = when (item.itemId) {
            R.id.chatFragment -> FragmentTag.CHAT_FRAGMENT_TAG
            R.id.profileFragment -> FragmentTag.PROFILE_FRAGMENT_TAG
            else -> throw IllegalArgumentException("Unexpected tag")
        }
        addFragment(CustomFragmentFactory.create(tag))
        return true
    }

    override fun onBackPressed() {
        setPreviousItem()
        super.onBackPressed()
    }

    /**
     * set correct checked item for bottom navigation after back press
     */
    private fun setPreviousItem() {
        if (bottomNavigationView.visibility == View.GONE) {
            return
        }
        if (supportFragmentManager.backStackEntryCount <= 1) {
            bottomNavigationView.menu.getItem(MenuItemIdx.CHAT_IDX.value).isChecked = true
            return
        }
        bottomNavigationView.menu.getItem(MenuItemIdx.PROFILE_IDX.value).isChecked = true
    }
}
