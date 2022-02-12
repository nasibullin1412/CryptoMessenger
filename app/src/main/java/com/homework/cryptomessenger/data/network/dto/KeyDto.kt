package com.homework.cryptomessenger.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeyDto(
    @SerialName("publicKey")
    val key: String
)

