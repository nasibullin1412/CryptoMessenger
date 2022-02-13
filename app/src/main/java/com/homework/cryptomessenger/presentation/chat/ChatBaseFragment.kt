package com.homework.cryptomessenger.presentation.chat

import android.os.Bundle
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
        viewModel.doGetMessages(page = FIRST_PAGE, size = SIZE)
        initEditText()
    }

    private fun initListeners() {
        viewModel.chatViewState.observe(viewLifecycleOwner, { changeChatViewState(it) })
    }

    private fun changeChatViewState(viewState: ChatViewState) {
        when (viewState) {
            is ChatViewState.ErrorViewState -> {
                binding.progressBar.visibility = View.GONE
                showToast(viewState.throwable.message)
            }
            ChatViewState.ProgressViewState -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is ChatViewState.SuccessGetMessages -> {
                binding.progressBar.visibility = View.GONE
                viewModel.updateRecycleList(
                    oldList = chatAdapter.currentList,
                    newList = viewState.messageListPageEntity.messageList.map(messageEntityToItem)
                )
            }
            is ChatViewState.SuccessUpdateList -> {
                updateMessage(viewState.newList)
            }
        }
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
                    /* val numberOfMess =
                         chatAdapter.currentList.firstWithInstance<MessageItem>()?.messageId ?: 0
                     internalStore.accept(
                         Event.Ui.LoadNextPage(
                             streamItem = currentStream,
                             topicItem = currentTopic.copy(numberOfMess = numberOfMess),
                             currId = currId
                         )
                     )*/
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
        imgSend.background =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_vector, context?.theme)
        etMessage.run {
            addTextChangedListener {
                binding.imgSend.background = ResourcesCompat.getDrawable(
                    resources,
                    selectIcon(text.toString()),
                    context.theme
                )
            }
        }
    }

    private fun selectIcon(text: String): Int = if (text.isEmpty()) {
        R.drawable.ic_vector
    } else {
        R.drawable.ic_vector_send
    }

    companion object {
        const val FIRST_PAGE = 1
        const val SIZE = 1
    }
}
