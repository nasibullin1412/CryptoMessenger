package com.homework.cryptomessenger.presentation.chat.data

data class ChatItem(
    var id: Long,
    val text: String,
    val isCurrentUser: Boolean,
    val username: String
)
