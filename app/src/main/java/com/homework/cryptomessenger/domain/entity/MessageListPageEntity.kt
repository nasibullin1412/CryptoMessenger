package com.homework.cryptomessenger.domain.entity

class MessageListPageEntity(
    val messageList: List<MessageEntity>,
    val size: Int,
    val isLastPage: Boolean
)
