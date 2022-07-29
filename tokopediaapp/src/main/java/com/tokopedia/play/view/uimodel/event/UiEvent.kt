package com.tokopedia.play.view.uimodel.event

import com.tokopedia.play.view.uimodel.ChatUiModel

sealed class UiEvent

data class ShowRealTimeChat(val chat: ChatUiModel): UiEvent()
