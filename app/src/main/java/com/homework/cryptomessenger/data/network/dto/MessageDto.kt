package com.homework.cryptomessenger.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(val id: Int, val text: String, val user: UserDto)
