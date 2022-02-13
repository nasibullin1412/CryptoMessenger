package com.homework.cryptomessenger.data.network.mappers

import com.homework.cryptomessenger.data.network.dto.MessageListResponseDto
import com.homework.cryptomessenger.domain.entity.MessageListPageEntity

class MessageResponseToEntity : (MessageListResponseDto) -> (MessageListPageEntity) {

    private val messageDtoToEntity = MessageDtoToEntity()

    override fun invoke(messageListResponseDto: MessageListResponseDto) =
        messageListResponseDto.run {
            MessageListPageEntity(
                messageList = content.map(messageDtoToEntity),
                size = size,
                isLastPage = number == totalPages
            )
        }
}
