package com.tokopedia.play.data

import com.tokopedia.play.data.model.ContentInfo
import com.tokopedia.play.data.model.UserInfo
import com.tokopedia.play.view.uimodel.ChatUiModel


/**
 * Created by mzennis on 15/06/20.
 */
object PlayMocker {

    fun getMockContentInfo(): ContentInfo = ContentInfo(
        id = "123",
        title = "Trend Fashion Pria Kekinian",
        isLive = true,
        totalView = 10L,
        videoUrl = "https://assets.mixkit.co/videos/preview/mixkit-man-under-multicolored-lights-1237-large.mp4"
    )

    fun getMockUserInfo(): UserInfo = UserInfo(
        userId = "1",
        username = "Danny"
    )

    fun getMockTotalView(currentTotalView: Long): Long = currentTotalView + (0..10).random()

    fun getMockChat(): ChatUiModel {
        return ChatUiModel(
            sender = listOf("John", "Doe", "Janice", "Alice").random(),
            message = listOf("keren abis!", "bahannya apa ka?", "topi nya ready ka?").random()
        )
    }
}