package com.homework.cryptomessenger.data.repositoryimpl

import com.homework.cryptomessenger.App
import com.homework.cryptomessenger.data.network.ApiService
import com.homework.cryptomessenger.data.network.NetworkConstants.PAGING_SORT_TYPE
import com.homework.cryptomessenger.data.network.mappers.MessageResponseToEntity
import com.homework.cryptomessenger.domain.entity.MessageListPageEntity
import com.homework.cryptomessenger.domain.repository.MessageRepository
import com.homework.myapplication.domain.repository.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class MessageRepositoryImpl : BaseDataSource(), MessageRepository {

    private val apiService: ApiService = App.instance.apiService
    private val messageResponseToEntity = MessageResponseToEntity()

    override suspend fun getMessages(size: Int, page: Int): Flow<MessageListPageEntity> =
        flow {
            val queryMap = mapOf("size" to size.toString(), "page" to page.toString(), "sort" to PAGING_SORT_TYPE)
            val result = safeApiCall { apiService.getMessages(queryMap) }
            if (result.data != null) {
                emit(messageResponseToEntity(result.data))
            } else {
                throw IOException(result.message)
            }
        }.flowOn(Dispatchers.IO)
}
