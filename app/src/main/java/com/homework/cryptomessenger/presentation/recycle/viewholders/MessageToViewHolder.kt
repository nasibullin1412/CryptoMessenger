package com.homework.cryptomessenger.presentation.recycle.viewholders

import com.homework.cryptomessenger.R
import com.homework.cryptomessenger.databinding.ItemMessageToBinding
import com.homework.cryptomessenger.presentation.chat.data.ChatItem

class MessageToViewHolder(
    private val viewBinding: ItemMessageToBinding
) : ChatViewHolder(viewBinding.root) {

    fun bind(messageItem: ChatItem) {
        with(viewBinding) {
            tvMessageContentTo.text = messageItem.text
            cvMessageTo.setBackgroundResource(R.drawable.bg_custom_message)
        }
    }
}
