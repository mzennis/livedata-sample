package com.tokopedia.play.view.uimodel


/**
 * Created by mzennis on 16/06/20.
 */
sealed class VideoState {
    object NoMedia: VideoState()
    object Buffering: VideoState()
    object Playing: VideoState()
    object Pause: VideoState()
    object Ended: VideoState()
}