package com.homework.cryptomessenger.data.crypto

import android.util.Base64
import com.google.crypto.tink.subtle.Hkdf
import com.google.crypto.tink.subtle.X25519
import com.homework.cryptomessenger.App
import com.homework.cryptomessenger.data.network.ApiService
import com.homework.cryptomessenger.data.network.dto.KeyDto

class KeyExchangeApi {

    private val apiService: ApiService = App.instance.apiService

    private lateinit var jwtToken: String
    private lateinit var privateKey: ByteArray
    private lateinit var publicKeyRaw: ByteArray
    private lateinit var sharedKey: ByteArray
    private lateinit var publicKeyRemote: String

    suspend operator fun invoke(jwtToken: String): ByteArray {
        this.jwtToken = jwtToken
        initializeHandshake()
        sendPublicKey()
        createShared()
        return sessionKey()
    }

    private fun sessionKey(): ByteArray {
        val salt = SALT.toByteArray()
        return Hkdf.computeHkdf(
            MAC_ALGORITHM,
            sharedKey,
            salt,
            null,
            32
        )
    }

    private fun createShared() {
        val publicRemoteKey: ByteArray = Base64.decode(publicKeyRemote, Base64.DEFAULT)
        sharedKey = X25519.computeSharedSecret(privateKey, publicRemoteKey)
    }

    private fun initializeHandshake() {
        privateKey = X25519.generatePrivateKey()
        publicKeyRaw = X25519.publicFromPrivate(privateKey)
    }

    private suspend fun sendPublicKey() {
        val base64 = Base64.encodeToString(
            publicKeyRaw,
            Base64.DEFAULT
        )
        val result = apiService.sendPublicKey(cryptoKey = KeyDto(base64))
        with(result) {
            publicKeyRemote = body()?.key ?: throw IllegalArgumentException(message())
        }
    }

    companion object {
        const val SALT = "secret"
        const val MAC_ALGORITHM = "HMACSHA256"
    }
}
