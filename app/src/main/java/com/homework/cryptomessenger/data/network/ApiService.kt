package com.homework.cryptomessenger.data.network

import com.homework.cryptomessenger.data.network.NetworkConstants.BASE_URL
import com.homework.cryptomessenger.data.network.body.AuthBody
import com.homework.cryptomessenger.data.network.body.MessageBody
import com.homework.cryptomessenger.data.network.dto.AuthDto
import com.homework.cryptomessenger.data.network.dto.KeyDto
import com.homework.cryptomessenger.data.network.dto.MessageDto
import com.homework.cryptomessenger.data.network.dto.MessageListResponseDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface ApiService {

    @POST("authenticate")
    suspend fun authUser(@Body authBody: AuthBody): Response<AuthDto>

    @POST("crypto/handshake")
    suspend fun sendPublicKey(
        @Body cryptoKey: KeyDto
    ): Response<KeyDto>

    @GET("message")
    suspend fun getMessages(
        @QueryMap queryMap: Map<String, String>
    ): Response<MessageListResponseDto>

    @POST("message")
    suspend fun sendMessage(
        @Body messageDto: MessageBody
    ): Response<MessageDto>

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
