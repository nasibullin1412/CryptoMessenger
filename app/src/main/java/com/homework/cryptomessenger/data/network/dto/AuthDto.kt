package com.homework.cryptomessenger.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthDto(val access: String, val refresh: String, val user: UserDto)
