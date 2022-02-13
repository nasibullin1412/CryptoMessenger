package com.homework.cryptomessenger.data.network.mappers

import com.homework.cryptomessenger.data.network.body.AuthBody
import com.homework.cryptomessenger.domain.entity.AuthEntity

class AuthEntityToBody : (AuthEntity) -> (AuthBody) {
    override fun invoke(authEntity: AuthEntity): AuthBody = authEntity.run {
        AuthBody(username = username, password = password)
    }
}
