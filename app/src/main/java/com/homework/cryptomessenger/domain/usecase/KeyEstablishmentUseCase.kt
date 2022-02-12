package com.homework.cryptomessenger.domain.usecase

import com.homework.cryptomessenger.data.repositoryimpl.AuthRepositoryImpl
import com.homework.cryptomessenger.domain.entity.AuthEntity
import com.homework.cryptomessenger.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class KeyEstablishmentUseCase {
    private val authRepository: AuthRepository = AuthRepositoryImpl()

    suspend operator fun invoke(token: String): Flow<Boolean> {
        return authRepository.sessionKeyEstablishment(token)
    }
}