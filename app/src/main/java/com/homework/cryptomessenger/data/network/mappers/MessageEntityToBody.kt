package com.homework.cryptomessenger.data.network.mappers

import android.util.Base64
import com.homework.cryptomessenger.data.crypto.CryptoManager
import com.homework.cryptomessenger.data.network.body.AuthBody
import com.homework.cryptomessenger.data.network.body.MessageBody
import com.homework.cryptomessenger.data.prefs.SharedPrefs
import com.homework.cryptomessenger.domain.entity.MessageEntity
import java.nio.charset.StandardCharsets

class MessageEntityToBody : (MessageEntity) -> (MessageBody) {
    override fun invoke(messageEntity: MessageEntity): MessageBody = messageEntity.run {
        val sessionKey = SharedPrefs.getSharedPreferenceString(SharedPrefs.SESSION_KEY)
        val encrypted = Base64.encodeToString(
            CryptoManager.encrypt(
                plainText = text.byteInputStream(StandardCharsets.UTF_8).readBytes(),
                key = Base64.decode(sessionKey, Base64.DEFAULT)
            ),
            Base64.DEFAULT
        )
        MessageBody(
            text = encrypted,
            user = AuthBody(null, null)
        )
    }
}
