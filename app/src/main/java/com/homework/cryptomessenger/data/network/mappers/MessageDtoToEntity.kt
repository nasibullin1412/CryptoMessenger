package com.homework.cryptomessenger.data.network.mappers

import android.util.Base64
import com.homework.cryptomessenger.data.crypto.CryptoManager
import com.homework.cryptomessenger.data.network.dto.MessageDto
import com.homework.cryptomessenger.data.prefs.SharedPrefs
import com.homework.cryptomessenger.data.prefs.SharedPrefs.CURR_USER_ID
import com.homework.cryptomessenger.domain.entity.MessageEntity

class MessageDtoToEntity : (MessageDto) -> (MessageEntity) {
    override fun invoke(messageDto: MessageDto): MessageEntity {
        val sessionKey = SharedPrefs.getSharedPreferenceString(SharedPrefs.SESSION_KEY)
        val currId = SharedPrefs.getSharedPreferenceLong(CURR_USER_ID)
        val decrypted = String(
            CryptoManager.decrypt(
                cipherText = Base64.decode(messageDto.text, Base64.DEFAULT),
                key = Base64.decode(sessionKey, Base64.DEFAULT)
            )
        )
        return messageDto.run {
            MessageEntity(
                id = id,
                text = decrypted,
                username = user.username,
                isCurrentUser = currId == user.id
            )
        }
    }
}
