package com.homework.cryptomessenger.presentation.chat

import com.homework.cryptomessenger.presentation.chat.data.ChatItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class UpdateRecycleList {
    suspend operator fun invoke(
        oldList: List<ChatItem>,
        newList: List<ChatItem>
    ): Flow<List<ChatItem>> = flow {
        val result = newList + oldList
        result.forEachIndexed { idx, item -> item.id = idx.toLong() }
        emit(result)
    }.flowOn(Dispatchers.Default)
}
