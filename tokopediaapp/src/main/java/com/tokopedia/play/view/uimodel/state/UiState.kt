package com.tokopedia.play.view.uimodel.state

import com.tokopedia.play.data.PlayMocker
import com.tokopedia.play.view.uimodel.ContentInfoUiModel
import com.tokopedia.play.view.uimodel.TotalViewUiModel
import com.tokopedia.play.view.uimodel.mapper.PlayMapper

data class UiState(
    val contentInfo: ContentInfoUiModel,
    val totalView: TotalViewUiModel,
    val videoUiState: VideoUiState,
) {
    companion object {

        private val contentInfo =  PlayMocker.getMockContentInfo()

        val Initial = UiState(
            contentInfo = PlayMapper.getContentInfo(contentInfo),
            totalView = PlayMapper.getTotalView(contentInfo),
            videoUiState = VideoUiState.Loading,
        )
    }
}

sealed class VideoUiState {
    object Loading: VideoUiState()
    object Playing: VideoUiState()
    object Ended: VideoUiState()
}
