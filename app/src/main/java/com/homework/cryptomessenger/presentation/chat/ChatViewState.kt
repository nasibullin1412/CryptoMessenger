package com.homework.cryptomessenger.presentation.chat

import com.homework.cryptomessenger.domain.entity.MessageListPageEntity
import com.homework.cryptomessenger.presentation.chat.data.ChatItem

sealed class ChatViewState {
    class SuccessGetMessages(val messageListPageEntity: MessageListPageEntity) : ChatViewState()
    class SuccessUpdateList(val newList: List<ChatItem>) : ChatViewState()
    class SuccessUpdate(val id: Long): ChatViewState()
    class ErrorUpdate(val throwable: Throwable): ChatViewState()
    class ErrorViewState(val throwable: Throwable) : ChatViewState()
    object SuccessSendMessage : ChatViewState()
    object ProgressViewState : ChatViewState()
}
