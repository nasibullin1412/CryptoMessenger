package com.homework.cryptomessenger.domain.repository

import com.homework.cryptomessenger.domain.entity.AuthEntity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun authUser(authEntity: AuthEntity): Flow<String>
    suspend fun sessionKeyEstablishment(token: String): Flow<Boolean>
}
