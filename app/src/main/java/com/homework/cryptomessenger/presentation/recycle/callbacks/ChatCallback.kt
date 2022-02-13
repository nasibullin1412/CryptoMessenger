package com.homework.cryptomessenger.presentation.recycle.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.homework.cryptomessenger.presentation.chat.data.ChatItem

class ChatCallback : DiffUtil.ItemCallback<ChatItem>() {
    override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
        return oldItem == newItem
    }
}
