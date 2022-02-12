package com.homework.cryptomessenger.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(val id: Long, val username: String)
