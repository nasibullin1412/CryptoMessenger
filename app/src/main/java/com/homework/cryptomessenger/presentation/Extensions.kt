package com.homework.cryptomessenger.presentation

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.homework.cryptomessenger.R
import com.homework.cryptomessenger.presentation.utils.CustomFragmentFactory
import com.homework.cryptomessenger.presentation.utils.FragmentTag

fun Fragment.showToast(message: String?) {
    Log.e("Toast", message ?: "")
    when {
        message.isNullOrEmpty() -> {
            showToast("Error")
        }
        else -> Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}

fun AppCompatActivity.addFragmentWithoutBackstack(customFragmentFactory: CustomFragmentFactory) {
    if (supportFragmentManager.backStackEntryCount != 0) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
    supportFragmentManager.beginTransaction()
        .replace(
            R.id.nav_host_fragment,
            customFragmentFactory.fragment,
            customFragmentFactory.fragmentTag.value
        )
        .commit()
}

fun AppCompatActivity.addFragment(customFragmentFactory: CustomFragmentFactory) {
    val tag = customFragmentFactory.fragmentTag
    if (customFragmentFactory.fragmentTag == FragmentTag.AUTH_FRAGMENT_TAG) {
        addFragmentWithoutBackstack(customFragmentFactory = customFragmentFactory)
        return
    }
    supportFragmentManager.popBackStack(tag.value, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    supportFragmentManager.beginTransaction()
        .replace(R.id.nav_host_fragment, customFragmentFactory.fragment, tag.value)
        .addToBackStack(tag.value)
        .commit()
}