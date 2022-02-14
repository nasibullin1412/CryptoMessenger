package com.homework.cryptomessenger.presentation.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.cryptomessenger.domain.usecase.CheckUpdateUseCase
import com.homework.cryptomessenger.domain.usecase.GetMessagesUseCase
import com.homework.cryptomessenger.domain.usecase.SendMessageUseCase
import com.homework.cryptomessenger.presentation.chat.data.ChatItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatViewModel : ViewModel() {

    val chatViewState: LiveData<ChatViewState> get() = _chatViewState
    private val _chatViewState = MutableLiveData<ChatViewState>()

    private val getMessagesUseCase: GetMessagesUseCase = GetMessagesUseCase()
    private val updateRecycle: UpdateRecycleList = UpdateRecycleList()
    private val sendMessagesUseCase: SendMessageUseCase = SendMessageUseCase()
    private val checkUpdateUseCase: CheckUpdateUseCase = CheckUpdateUseCase()

    fun doGetMessages(size: Int, page: Int) = viewModelScope.launch {
        _chatViewState.value = ChatViewState.ProgressViewState
        getMessagesUseCase(size = size, page = page).catch { error ->
            _chatViewState.value = ChatViewState.ErrorViewState(error)
        }.collect {
            _chatViewState.value = ChatViewState.SuccessGetMessages(it)
        }
    }

    fun updateRecycleList(oldList: List<ChatItem>, newList: List<ChatItem>) =
        viewModelScope.launch {
            updateRecycle(
                oldList = oldList,
                newList = (newList as MutableList<ChatItem>)
            ).catch { error ->
                _chatViewState.value = ChatViewState.ErrorViewState(error)
            }.collect {
                _chatViewState.value = ChatViewState.SuccessUpdateList(it)
            }
        }

    fun sendMessage(text: String) = viewModelScope.launch {
        sendMessagesUseCase(text).catch { error ->
            _chatViewState.value = ChatViewState.ErrorViewState(error)
        }.collect {
            _chatViewState.value = ChatViewState.SuccessSendMessage
        }
    }

    fun initCheck() = viewModelScope.launch {
        withContext(Dispatchers.Default) {
            while (true) {
                checkUpdateUseCase().catch { error ->
                    _chatViewState.postValue(ChatViewState.ErrorViewState(error))
                }.collect {
                    _chatViewState.postValue(ChatViewState.SuccessUpdate(it))
                }
                delay(DELAY_TIME)
            }
        }
    }

    companion object {
        const val DELAY_TIME = 2500L
    }
}
