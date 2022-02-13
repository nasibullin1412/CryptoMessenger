package com.homework.cryptomessenger.data.repositoryimpl

import android.util.Base64
import com.homework.cryptomessenger.App
import com.homework.cryptomessenger.data.crypto.KeyExchangeApi
import com.homework.cryptomessenger.data.network.mappers.AuthEntityToBody
import com.homework.cryptomessenger.data.prefs.SharedPrefs
import com.homework.cryptomessenger.data.prefs.SharedPrefs.CURR_USER_ID
import com.homework.cryptomessenger.data.prefs.SharedPrefs.SESSION_KEY
import com.homework.cryptomessenger.data.prefs.SharedPrefs.TOKEN_KEY
import com.homework.cryptomessenger.domain.entity.AuthEntity
import com.homework.cryptomessenger.domain.repository.AuthRepository
import com.homework.cryptomessenger.domain.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class AuthRepositoryImpl : BaseDataSource(), AuthRepository {

    private val apiService = App.instance.apiService
    private val authEntityToBody: AuthEntityToBody = AuthEntityToBody()
    private val keyExchangeApi: KeyExchangeApi = KeyExchangeApi()

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
        val sessionKey = keyExchangeApi(token)
        SharedPrefs.setValueToSharedPreference(
            SESSION_KEY,
            Base64.encodeToString(sessionKey, Base64.DEFAULT)
        )
        emit(true)
    }.flowOn(Dispatchers.IO)
}
