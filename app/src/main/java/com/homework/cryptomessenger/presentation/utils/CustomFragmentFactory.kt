package com.homework.cryptomessenger.presentation.utils

import androidx.fragment.app.Fragment
import com.homework.cryptomessenger.presentation.authorization.AuthFragment
import com.homework.cryptomessenger.presentation.chat.ChatFragment

class CustomFragmentFactory(var fragment: Fragment, var fragmentTag: FragmentTag) {
    companion object {
        fun create(fragmentTag: FragmentTag): CustomFragmentFactory {
            val fragment: Fragment = when (fragmentTag) {
                FragmentTag.CHAT_FRAGMENT_TAG -> ChatFragment()
                FragmentTag.PROFILE_FRAGMENT_TAG -> throw NotImplementedError()
                FragmentTag.AUTH_FRAGMENT_TAG -> AuthFragment()
            }
            return CustomFragmentFactory(fragment, fragmentTag)
        }
    }
}
