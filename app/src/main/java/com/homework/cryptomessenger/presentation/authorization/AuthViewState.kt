package com.homework.cryptomessenger.presentation.authorization

sealed class AuthViewState {
    class SuccessAuth(val token: String) : AuthViewState()
    object SuccessKeyEstablishment : AuthViewState()
    class ErrorAuth(val throwable: Throwable) : AuthViewState()
}
