package com.homework.cryptomessenger.presentation.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.homework.cryptomessenger.databinding.AuthFragmentBinding

abstract class AuthBaseFragment : Fragment() {

    protected val binding get() = _binding!!
    protected val viewModel: AuthViewModel by viewModels()
    private var _binding: AuthFragmentBinding? = null

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

    private fun initButton() {
        binding.btnLogin.setOnClickListener {
            authAction()
        }
    }

    internal abstract fun authAction()
    internal abstract fun initObservers()
    internal abstract fun screenEffects(viewState: AuthViewState)
}
