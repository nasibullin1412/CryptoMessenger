package com.homework.cryptomessenger.data.network

import com.homework.cryptomessenger.data.network.NetworkConstants.BASE_URL
import com.homework.cryptomessenger.data.network.body.AuthBody
import com.homework.cryptomessenger.data.network.dto.AuthDto
import com.homework.cryptomessenger.data.network.dto.KeyDto
import com.homework.cryptomessenger.data.network.dto.MessageListResponseDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*

interface ApiService {

    @POST("authenticate")
    suspend fun authUser(@Body authBody: AuthBody): Response<AuthDto>

    @POST("crypto/handshake")
    suspend fun sendPublicKey(
        //@Header("Authorization") djangoToken: String?,
        @Body cryptoKey: KeyDto
    ): Response<KeyDto>

    @GET("message")
    suspend fun getMessages(
        @QueryMap queryMap: Map<String, Int>
    ): Response<MessageListResponseDto>

    companion object {
        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .setClient()
                .addJsonConverter()
                .build()
                .create(ApiService::class.java)
        }
    }
}
