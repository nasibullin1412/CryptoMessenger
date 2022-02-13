package com.homework.cryptomessenger.domain.repository

import com.homework.cryptomessenger.domain.entity.MessageListPageEntity
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun getMessages(size: Int, page: Int): Flow<MessageListPageEntity>
}
