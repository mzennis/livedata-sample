package com.tokopedia.play.view.uimodel.mapper

import com.tokopedia.play.data.model.ContentInfo
import com.tokopedia.play.view.custom.VideoState
import com.tokopedia.play.view.uimodel.ContentInfoUiModel
import com.tokopedia.play.view.uimodel.TotalViewUiModel
import com.tokopedia.play.view.uimodel.state.VideoUiState


/**
 * Created by mzennis on 15/06/20.
 */
object PlayMapper {

    fun getContentInfo(contentInfo: ContentInfo) = ContentInfoUiModel(
        id = contentInfo.id,
        title = contentInfo.title,
        isLive = contentInfo.isLive,
        videoUrl = contentInfo.videoUrl,
    )

    fun getTotalView(contentInfo: ContentInfo) = TotalViewUiModel(
        totalView = contentInfo.totalView
    )

    fun getVideoState(state: VideoState): VideoUiState {
        return when(state) {
            VideoState.Buffering -> VideoUiState.Loading
            VideoState.Ended -> VideoUiState.Ended
            VideoState.NoMedia -> VideoUiState.Loading
            VideoState.Pause -> VideoUiState.Playing
            VideoState.Playing -> VideoUiState.Playing
        }
    }
}