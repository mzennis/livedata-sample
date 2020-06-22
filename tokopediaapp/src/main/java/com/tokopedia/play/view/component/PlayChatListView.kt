package com.tokopedia.play.view.component

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tokopedia.play.R
import com.tokopedia.play.view.adapter.ChatAdapter
import com.tokopedia.play.view.uimodel.ChatUiModel


/**
 * Created by mzennis on 16/06/20.
 */
class PlayChatListView(
    container: ViewGroup
) : PartialView(container, R.id.layout_chat_list){

    private var chatAdapter: ChatAdapter = ChatAdapter()
    private var recyclerView: RecyclerView = container.findViewById(R.id.rv_chat_list)

    private val adapterObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            recyclerView.postDelayed({
                recyclerView.smoothScrollToPosition(chatAdapter.lastIndex)
            }, 100)
        }
    }

    init {
        with(recyclerView) {
            setHasFixedSize(true)
            adapter = chatAdapter

            chatAdapter.registerAdapterDataObserver(adapterObserver)
        }
    }

    fun addChat(chat: ChatUiModel) {
        chatAdapter.add(chat)
    }
}