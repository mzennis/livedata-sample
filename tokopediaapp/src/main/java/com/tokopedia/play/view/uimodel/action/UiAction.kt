package com.tokopedia.play.view.uimodel.action

sealed class UiAction

data class SendChat(val message: String): UiAction()
