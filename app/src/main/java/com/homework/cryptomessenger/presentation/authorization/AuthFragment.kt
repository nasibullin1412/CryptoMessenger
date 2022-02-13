package com.homework.cryptomessenger.presentation.authorization

import com.homework.cryptomessenger.domain.entity.AuthEntity
import com.homework.cryptomessenger.presentation.showToast
import com.homework.cryptomessenger.presentation.utils.CustomFragmentFactory
import com.homework.cryptomessenger.presentation.utils.FragmentTag

class AuthFragment : AuthBaseFragment() {
    override fun authAction(): Unit = with(binding) {
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()
        viewModel.doAuth(AuthEntity(username = username, password = password))
    }

    override fun initObservers() {
        viewModel.authViewState.observe(viewLifecycleOwner, { screenEffects(it) })
    }

    override fun screenEffects(viewState: AuthViewState) {
        when (viewState) {
            is AuthViewState.ErrorAuth -> {
                showToast(viewState.throwable.message)
            }
            is AuthViewState.SuccessAuth -> {
                viewModel.doKeyExchange(viewState.token)
            }
            AuthViewState.SuccessKeyEstablishment -> {
                navigateController?.navigateFragment(
                    CustomFragmentFactory.create(FragmentTag.CHAT_FRAGMENT_TAG)
                )
            }
        }
    }
}
