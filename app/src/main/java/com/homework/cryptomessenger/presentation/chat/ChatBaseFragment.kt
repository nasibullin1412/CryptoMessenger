package com.homework.cryptomessenger.presentation.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.homework.cryptomessenger.R
import com.homework.cryptomessenger.databinding.ChatFragmentBinding
import com.homework.cryptomessenger.presentation.chat.data.ChatItem
import com.homework.cryptomessenger.presentation.chat.data.MessageEntityToItem
import com.homework.cryptomessenger.presentation.recycle.adapters.ChatAdapter
import com.homework.cryptomessenger.presentation.showToast

abstract class ChatBaseFragment : Fragment() {

    private val binding get() = _binding!!
    private val viewModel: ChatViewModel by viewModels()

    private var _binding: ChatFragmentBinding? = null
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var scrollListener: PagingScrollListener
    private val messageEntityToItem: MessageEntityToItem = MessageEntityToItem()
    private var currLastId: Long = 0L
    private var isFirst = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChatFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        viewModel.doGetMessages(page = FIRST_PAGE, size = PagingScrollListener.PAGE_SIZE)
        initEditText()
    }

    private fun initListeners() {
        viewModel.chatViewState.observe(viewLifecycleOwner, { changeChatViewState(it) })
        binding.imgSend.setOnClickListener {
            viewModel.sendMessage(binding.etMessage.text.toString())
            binding.etMessage.setText("")
        }
        binding.tvNewMessage.setOnClickListener {
            reGetAllMessages()
            binding.tvNewMessage.visibility = View.GONE
        }
    }

    private fun changeChatViewState(viewState: ChatViewState) {
        when (viewState) {
            is ChatViewState.ErrorViewState -> {
                binding.progressBar.visibility = View.GONE
                showToast(viewState.throwable.message)
                binding.nsvErrorConnection.visibility = View.VISIBLE
            }
            ChatViewState.ProgressViewState -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is ChatViewState.SuccessGetMessages -> {
                binding.progressBar.visibility = View.GONE
                val lastId = viewState.messageListPageEntity.messageList.firstOrNull()?.id ?: 0
                if (lastId > currLastId) {
                    currLastId = lastId.toLong()
                }
                viewModel.updateRecycleList(
                    oldList = chatAdapter.currentList,
                    newList = viewState.messageListPageEntity.messageList.map(messageEntityToItem)
                )
            }
            is ChatViewState.SuccessUpdateList -> {
                updateMessage(viewState.newList)
                if (isFirst) {
                    isFirst = false
                    viewModel.initCheck()
                }
            }
            ChatViewState.SuccessSendMessage -> {
                reGetAllMessages()
            }
            is ChatViewState.ErrorUpdate -> {
                showToast(viewState.throwable.message)
            }
            is ChatViewState.SuccessUpdate -> {
                Log.d("UpdateMessage", "Success updated")
                if (currLastId < viewState.id) {
                    binding.tvNewMessage.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun reGetAllMessages() {
        scrollListener.numberOfPage = 1
        chatAdapter.submitList(emptyList())
        viewModel.doGetMessages(size = PagingScrollListener.PAGE_SIZE, page = FIRST_PAGE)
    }

    private fun initViews() {
        initRecycleViewImpl()
        initBackButton()
    }

    private fun updateMessage(newList: List<ChatItem>) {
        if (newList.isEmpty()) {
            chatAdapter.submitList(emptyList())
            return
        }
        chatAdapter.submitList(newList)
    }

    private fun initRecycleViewImpl() {
        with(binding.rvMessage) {
            chatAdapter = ChatAdapter()
            adapter = chatAdapter
            val currLayoutManager = LinearLayoutManager(context).apply { stackFromEnd = true }
            layoutManager = currLayoutManager
            scrollListener = object : PagingScrollListener(currLayoutManager) {
                override fun onLoadMore(top: Boolean): Boolean {
                    viewModel.doGetMessages(page = numberOfPage, size = PAGE_SIZE)
                    return true
                }
            }
            addOnScrollListener(scrollListener)
        }
    }

    private fun initBackButton() {
        binding.imgBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initEditText() = with(binding) {
        imgSend.background = ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_vector,
            context?.theme
        )
        etMessage.run {
            addTextChangedListener {
                with(imgSend) {
                    background = ResourcesCompat.getDrawable(
                        resources,
                        selectIcon(text.toString()),
                        context.theme
                    )
                    isActivated = text.isEmpty().not()
                }
            }
        }
    }

    private fun selectIcon(text: String): Int = if (text.isEmpty()) {
        R.drawable.ic_vector
    } else {
        R.drawable.ic_vector_send
    }

    companion object {
        const val FIRST_PAGE = 0
    }
}
