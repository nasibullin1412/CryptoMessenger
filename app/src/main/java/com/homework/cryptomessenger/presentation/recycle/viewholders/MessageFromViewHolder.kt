package com.homework.cryptomessenger.presentation.recycle.viewholders

import com.homework.cryptomessenger.R
import com.homework.cryptomessenger.databinding.ItemMessageFromBinding
import com.homework.cryptomessenger.presentation.chat.data.ChatItem

class MessageFromViewHolder(
    viewBinding: ItemMessageFromBinding
) : ChatViewHolder(viewBinding.root) {

    private val customMessageViewGroupBinding = ItemMessageFromBinding.bind(viewBinding.root)

    fun bind(messageItem: ChatItem) {
        with(customMessageViewGroupBinding) {
            tvMessageContentFrom.text = messageItem.text
            cvMessageFrom.setBackgroundResource(R.drawable.bg_custom_message)
        }
    }
}
