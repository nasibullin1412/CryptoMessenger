package com.homework.cryptomessenger.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.homework.cryptomessenger.R
import com.homework.cryptomessenger.presentation.utils.CustomFragmentFactory
import com.homework.cryptomessenger.presentation.utils.FragmentTag
import com.homework.cryptomessenger.presentation.utils.NavigateController

class MainActivity : AppCompatActivity(), NavigateController {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fragmentFactory = CustomFragmentFactory.create(FragmentTag.AUTH_FRAGMENT_TAG)
            addFragment(fragmentFactory)
        }
    }

    override fun navigateFragment(customFragmentFactory: CustomFragmentFactory) {
        addFragment(customFragmentFactory)
    }
}
