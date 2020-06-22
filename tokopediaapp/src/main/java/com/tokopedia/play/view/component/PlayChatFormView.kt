package com.tokopedia.play.view.component

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import com.tokopedia.play.R


/**
 * Created by mzennis on 16/06/20.
 */
class PlayChatFormView(
    container: ViewGroup,
    private val listener: Listener
) : PartialView(container, R.id.layout_chat_form){

    private var etForm: AppCompatEditText = container.findViewById(R.id.et_chat)
    private var ivSend: AppCompatImageView = container.findViewById(R.id.iv_send)

    private val chatTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(text: Editable?) {
            if(text.isNullOrEmpty()) ivSend.visibility = View.INVISIBLE
            else ivSend.visibility = View.VISIBLE
        }

        override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    init {
        etForm.addTextChangedListener(chatTextWatcher)
        ivSend.setOnClickListener {
            listener.onSendClicked(etForm.text.toString())
            etForm.setText("")
        }
    }

    interface Listener {
        fun onSendClicked(message: String)
    }
}