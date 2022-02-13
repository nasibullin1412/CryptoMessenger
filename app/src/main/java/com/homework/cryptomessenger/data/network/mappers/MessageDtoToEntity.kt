package com.homework.cryptomessenger.data.network.mappers

import com.homework.cryptomessenger.data.network.dto.MessageDto
import com.homework.cryptomessenger.data.prefs.SharedPrefs
import com.homework.cryptomessenger.data.prefs.SharedPrefs.CURR_USER_ID
import com.homework.cryptomessenger.domain.entity.MessageEntity

class MessageDtoToEntity : (MessageDto) -> (MessageEntity) {
    override fun invoke(messageDto: MessageDto): MessageEntity {
        val currId = SharedPrefs.getSharedPreferenceLong(CURR_USER_ID)
        return messageDto.run {
            MessageEntity(
                id = id,
                text = text,
                username = user.username,
                isCurrentUser = currId == user.id
            )
        }
    }
}
