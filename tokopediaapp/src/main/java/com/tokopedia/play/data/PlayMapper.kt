package com.tokopedia.play.data

import com.tokopedia.play.data.model.ContentInfo
import com.tokopedia.play.view.uimodel.ContentInfoUiModel
import com.tokopedia.play.view.uimodel.TotalViewUiModel
import com.tokopedia.play.view.uimodel.VideoPropertyUiModel
import com.tokopedia.play.view.uimodel.VideoState


/**
 * Created by mzennis on 15/06/20.
 */
object PlayMapper {

    fun getContentInfo(contentInfo: ContentInfo) = ContentInfoUiModel(
        title = contentInfo.title,
        isLive = contentInfo.isLive
    )

    fun getTotalView(contentInfo: ContentInfo) = TotalViewUiModel(
        totalView = contentInfo.totalView
    )

    fun getVideoProperty() = VideoPropertyUiModel(
        videoState = VideoState.NoMedia
    )
}