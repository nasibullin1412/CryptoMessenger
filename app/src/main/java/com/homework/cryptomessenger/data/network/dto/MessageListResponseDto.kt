package com.homework.cryptomessenger.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageListResponseDto(
    val content: List<MessageDto>,
    val size: Int,
    val number: Int,
    val totalPages: Int
)
