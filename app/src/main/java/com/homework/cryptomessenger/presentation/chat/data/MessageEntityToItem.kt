package com.homework.cryptomessenger.presentation.chat.data

import com.homework.cryptomessenger.domain.entity.MessageEntity

class MessageEntityToItem : (MessageEntity) -> (ChatItem) {
    override fun invoke(messageEntity: MessageEntity): ChatItem =
        messageEntity.run {
            ChatItem(
                id = id.toLong(),
                text = text,
                isCurrentUser = isCurrentUser,
                username = username
            )
        }
}
