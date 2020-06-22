package com.tokopedia.play.view.component

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.tokopedia.play.R


/**
 * Created by mzennis on 16/06/20.
 */
class PlayActionBarView(
    container: ViewGroup,
    listener: Listener
) : PartialView(container, R.id.layout_action_bar){

    private var tvTitle: AppCompatTextView = container.findViewById(R.id.tv_title)
    private var imgBack: AppCompatImageView = container.findViewById(R.id.img_back)

    init {
        imgBack.setOnClickListener { listener.onBackIconClicked() }
    }

    fun setTitle(title: String) {
        tvTitle.text = title
    }

    interface Listener {
        fun onBackIconClicked()
    }
}