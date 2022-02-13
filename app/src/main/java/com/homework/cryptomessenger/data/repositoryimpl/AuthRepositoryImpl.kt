package com.homework.cryptomessenger.data.repositoryimpl

import com.homework.cryptomessenger.App
import com.homework.cryptomessenger.data.crypto.CryptoManager
import com.homework.cryptomessenger.data.network.mappers.AuthEntityToBody
import com.homework.cryptomessenger.data.prefs.SharedPrefs
import com.homework.cryptomessenger.data.prefs.SharedPrefs.CURR_USER_ID
import com.homework.cryptomessenger.data.prefs.SharedPrefs.SESSION_KEY
import com.homework.cryptomessenger.data.prefs.SharedPrefs.TOKEN_KEY
import com.homework.cryptomessenger.domain.entity.AuthEntity
import com.homework.cryptomessenger.domain.repository.AuthRepository
import com.homework.myapplication.domain.repository.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class AuthRepositoryImpl : BaseDataSource(), AuthRepository {

    private val apiService = App.instance.apiService
    private val authEntityToBody: AuthEntityToBody = AuthEntityToBody()
    private val cryptoManager: CryptoManager = CryptoManager()

    override suspend fun authUser(authEntity: AuthEntity): Flow<String> = flow {
        val result = safeApiCall { apiService.authUser(authEntityToBody(authEntity)) }
        if (result.data != null) {
            with(result.data) {
                SharedPrefs.setValueToSharedPreference(TOKEN_KEY, access)
                SharedPrefs.setValueToSharedPreference(CURR_USER_ID, user.id)
                emit(access)
            }
        } else {
            throw IOException(result.message)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun sessionKeyEstablishment(token: String): Flow<Boolean> = flow {
        val sessionKey = cryptoManager(token)
        SharedPrefs.setValueToSharedPreference(SESSION_KEY, sessionKey.toString())
        emit(true)
    }.flowOn(Dispatchers.IO)
}
