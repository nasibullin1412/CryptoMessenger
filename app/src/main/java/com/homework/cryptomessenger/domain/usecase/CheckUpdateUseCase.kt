package com.homework.cryptomessenger.domain.usecase

import com.homework.cryptomessenger.data.repositoryimpl.MessageRepositoryImpl
import kotlinx.coroutines.flow.Flow

class CheckUpdateUseCase {
    private val messageRepositoryImpl = MessageRepositoryImpl()

    suspend operator fun invoke(): Flow<Long> {
        return messageRepositoryImpl.updateMessage()
    }
}
