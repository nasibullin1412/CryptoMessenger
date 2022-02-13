package com.homework.cryptomessenger.presentation.authorization

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.homework.cryptomessenger.databinding.AuthFragmentBinding
import com.homework.cryptomessenger.presentation.utils.NavigateController

abstract class AuthBaseFragment : Fragment() {

    protected val binding get() = _binding!!
    protected val viewModel: AuthViewModel by viewModels()
    private var _binding: AuthFragmentBinding? = null
    internal var navigateController: NavigateController? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigateController) {
            navigateController = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AuthFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
        initObservers()
    }

    override fun onDetach() {
        super.onDetach()
        navigateController = null
    }

    private fun initButton() {
        binding.btnLogin.setOnClickListener {
            authAction()
        }
    }

    internal abstract fun authAction()
    internal abstract fun initObservers()
    internal abstract fun screenEffects(viewState: AuthViewState)
}
