package com.homework.cryptomessenger.presentation.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.cryptomessenger.domain.entity.AuthEntity
import com.homework.cryptomessenger.domain.usecase.AuthUseCase
import com.homework.cryptomessenger.domain.usecase.KeyEstablishmentUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    val authViewState: LiveData<AuthViewState> get() = _authViewState
    private val _authViewState = MutableLiveData<AuthViewState>()

    private val authUseCase = AuthUseCase()
    private val keyEstablishmentUseCase = KeyEstablishmentUseCase()

    fun doAuth(authEntity: AuthEntity) = viewModelScope.launch {
        authUseCase(authEntity).catch { error ->
            _authViewState.value = AuthViewState.ErrorAuth(error)
        }.collect {
            _authViewState.value = AuthViewState.SuccessAuth(it)
        }
    }

    fun doKeyExchange(token: String) = viewModelScope.launch {
        keyEstablishmentUseCase(token).catch { error ->
            _authViewState.value = AuthViewState.ErrorAuth(error)
        }.collect {
            _authViewState.value = AuthViewState.SuccessKeyEstablishment
        }
    }
}
