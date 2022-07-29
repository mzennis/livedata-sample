package com.tokopedia.play.view.component

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.tokopedia.play.R
import com.tokopedia.play.view.uimodel.state.VideoUiState


/**
 * Created by mzennis on 16/06/20.
 */
class PlayLoadingView(
    container: ViewGroup
) : PartialView(container, R.id.layout_loading){

    private val imgLoading = container.findViewById<AppCompatImageView>(R.id.img_loading)

    fun setVideoState(state: VideoUiState) {
        when(state) {
            is VideoUiState.Loading -> showLoading()
            is VideoUiState.Playing -> hideLoading()
            is VideoUiState.Ended -> {}
        }
    }

    private fun showLoading() {
        if (imgLoading.drawable == null) {
            Glide.with(imgLoading.context)
                .asGif()
                .load(R.drawable.play_loading)
                .into(imgLoading)
        }
        imgLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        imgLoading.visibility = View.GONE
    }
}
