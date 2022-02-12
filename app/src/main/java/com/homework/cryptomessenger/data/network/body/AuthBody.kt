package com.homework.cryptomessenger.data.network.body

import kotlinx.serialization.Serializable

@Serializable
data class AuthBody(val username: String, val password: String)
