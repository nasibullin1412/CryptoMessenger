package com.homework.cryptomessenger.presentation.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.cryptomessenger.domain.usecase.GetMessagesUseCase
import com.homework.cryptomessenger.presentation.chat.data.ChatItem
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    val chatViewState: LiveData<ChatViewState> get() = _chatViewState
    private val _chatViewState = MutableLiveData<ChatViewState>()

    private val getMessagesUseCase: GetMessagesUseCase = GetMessagesUseCase()
    private val updateRecycle: UpdateRecycleList = UpdateRecycleList()

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
            updateRecycle(oldList = oldList, newList = newList).catch { error ->
                _chatViewState.value = ChatViewState.ErrorViewState(error)
            }.collect {
                _chatViewState.value = ChatViewState.SuccessUpdateList(it)
            }
        }
}
