package com.homework.cryptomessenger.data.network.body

import kotlinx.serialization.Serializable

@Serializable
data class MessageBody(val text: String, val user: AuthBody)
