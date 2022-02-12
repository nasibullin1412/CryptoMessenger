package com.homework.cryptomessenger.domain.usecase

import com.homework.cryptomessenger.data.repositoryimpl.AuthRepositoryImpl
import com.homework.cryptomessenger.domain.entity.AuthEntity
import com.homework.cryptomessenger.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthUseCase {
    private val authRepository: AuthRepository = AuthRepositoryImpl()

    suspend operator fun invoke(authEntity: AuthEntity): Flow<String> {
        return authRepository.authUser(authEntity)
    }
}
