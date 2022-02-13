package com.homework.cryptomessenger.presentation.recycle.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.homework.cryptomessenger.databinding.ItemMessageFromBinding
import com.homework.cryptomessenger.databinding.ItemMessageToBinding
import com.homework.cryptomessenger.presentation.chat.data.ChatItem
import com.homework.cryptomessenger.presentation.recycle.callbacks.ChatCallback
import com.homework.cryptomessenger.presentation.recycle.viewholders.ChatViewHolder
import com.homework.cryptomessenger.presentation.recycle.viewholders.MessageFromViewHolder
import com.homework.cryptomessenger.presentation.recycle.viewholders.MessageToViewHolder

class ChatAdapter : ListAdapter<ChatItem, ChatViewHolder>(ChatCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return when (ChatViewType.getChatViewTypeByInt(viewType)) {
            ChatViewType.MESSAGE_TO -> MessageToViewHolder(
                ItemMessageToBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
            ChatViewType.MESSAGE_FROM -> MessageFromViewHolder(
                ItemMessageFromBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MessageFromViewHolder -> holder.bind(item)
            is MessageToViewHolder -> holder.bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).run {
        if (isCurrentUser) {
            ChatViewType.MESSAGE_TO.value
        } else {
            ChatViewType.MESSAGE_FROM.value
        }
    }
}

enum class ChatViewType(val value: Int) {
    MESSAGE_TO(0),
    MESSAGE_FROM(1);

    companion object {
        fun getChatViewTypeByInt(value: Int) = values().first { it.value == value }
    }
}
