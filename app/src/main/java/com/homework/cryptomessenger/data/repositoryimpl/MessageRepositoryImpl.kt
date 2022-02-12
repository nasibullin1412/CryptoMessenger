package com.homework.cryptomessenger.data.repositoryimpl

import com.homework.cryptomessenger.App
import com.homework.cryptomessenger.data.network.ApiService
import com.homework.cryptomessenger.domain.repository.MessageRepository

class MessageRepositoryImpl : MessageRepository {

    private val apiService: ApiService = App.instance.apiService

    override fun getMessages(){

    }
}