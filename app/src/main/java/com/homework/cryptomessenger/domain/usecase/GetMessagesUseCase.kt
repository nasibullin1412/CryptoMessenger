package com.homework.cryptomessenger.domain.usecase

import com.homework.cryptomessenger.data.repositoryimpl.MessageRepositoryImpl
import com.homework.cryptomessenger.domain.entity.MessageListPageEntity
import com.homework.cryptomessenger.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow

class GetMessagesUseCase {
    private val messageRepository: MessageRepository = MessageRepositoryImpl()

    suspend operator fun invoke(size: Int, page: Int): Flow<MessageListPageEntity> {
        return messageRepository.getMessages(size = size, page = page)
    }
}
