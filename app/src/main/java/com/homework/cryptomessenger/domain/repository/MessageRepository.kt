package com.homework.cryptomessenger.domain.repository

import com.homework.cryptomessenger.domain.entity.MessageEntity
import com.homework.cryptomessenger.domain.entity.MessageListPageEntity
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun getMessages(size: Int, page: Int): Flow<MessageListPageEntity>
    suspend fun sendMessage(messageEntity: MessageEntity): Flow<MessageEntity>
    suspend fun updateMessage(): Flow<Long>
}
