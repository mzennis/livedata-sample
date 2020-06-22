package com.tokopedia.play.data.model


/**
 * Created by mzennis on 15/06/20.
 */
data class ContentInfo(
    val title: String,
    val isLive: Boolean,
    val totalView: Long,
    val videoUrl: String
)