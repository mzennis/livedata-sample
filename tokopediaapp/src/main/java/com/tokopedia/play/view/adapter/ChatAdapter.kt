package com.tokopedia.play.view.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.tokopedia.play.R
import com.tokopedia.play.view.uimodel.ChatUiModel


/**
 * Created by mzennis on 16/06/20.
 */
class ChatAdapter(private val chatList: MutableList<ChatUiModel> = arrayListOf()) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_play_chat, parent, false))
    }

    override fun getItemCount(): Int = chatList.size

    val lastIndex: Int
        get() = chatList.size - 1

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chatList[position])
    }

    fun add(chat: ChatUiModel) {
        chatList.add(chat)
        this.notifyItemInserted(itemCount)
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvChat = itemView.findViewById<AppCompatTextView>(R.id.tv_chat)

        fun bind(item: ChatUiModel) {
            val sender = SpannableString(item.sender)
            sender.setSpan(
                ForegroundColorSpan(Color.GREEN),
                0,
                item.sender.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tvChat.text = ""
            tvChat.append(sender)
            tvChat.append(" ")
            tvChat.append(item.message)
        }
    }
}