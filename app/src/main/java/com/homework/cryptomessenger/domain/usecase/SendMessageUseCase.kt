package com.homework.cryptomessenger.domain.usecase

import com.homework.cryptomessenger.data.repositoryimpl.MessageRepositoryImpl
import com.homework.cryptomessenger.domain.entity.MessageEntity
import com.homework.cryptomessenger.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow

class SendMessageUseCase {
    private val messageRepository: MessageRepository = MessageRepositoryImpl()

    suspend operator fun invoke(text: String): Flow<MessageEntity> {
        return messageRepository.sendMessage(
            MessageEntity(
                id = 0,
                text = text,
                username = "",
                isCurrentUser = true
            )
        )
    }
}
