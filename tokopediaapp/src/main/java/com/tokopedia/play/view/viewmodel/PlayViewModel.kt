package com.tokopedia.play.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.SimpleExoPlayer
import com.tokopedia.play.data.PlayMocker
import com.tokopedia.play.view.custom.PlayVideoManager
import com.tokopedia.play.view.uimodel.ChatUiModel
import com.tokopedia.play.view.uimodel.ContentInfoUiModel
import com.tokopedia.play.view.uimodel.TotalViewUiModel
import com.tokopedia.play.view.uimodel.action.SendChat
import com.tokopedia.play.view.uimodel.action.UiAction
import com.tokopedia.play.view.uimodel.event.ShowRealTimeChat
import com.tokopedia.play.view.uimodel.event.UiEvent
import com.tokopedia.play.view.uimodel.mapper.PlayMapper
import com.tokopedia.play.view.uimodel.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by mzennis on 15/06/20.
 */
@HiltViewModel
class PlayViewModel @Inject constructor(
    private val videoManager: PlayVideoManager,
): ViewModel() {

    val observableVideoPlayer: LiveData<SimpleExoPlayer> get() = videoManager.getVideoPlayer()

    private val _contentInfo = MutableStateFlow(ContentInfoUiModel.Empty)
    private val _totalView = MutableStateFlow(TotalViewUiModel.Empty)
    private val _videoState = videoManager.getVideoState().asFlow()

    val uiState: Flow<UiState> = combine(
        _contentInfo,
        _totalView,
        _videoState,
    ) { contentInfo, totalView, videoState ->
        UiState(
            contentInfo = contentInfo,
            totalView = totalView,
            videoUiState = PlayMapper.getVideoState(videoState)
        )
    }.flowOn(Dispatchers.Default)

    val uiEvent: Flow<UiEvent>
        get() = _uiEvent
    private val _uiEvent = MutableSharedFlow<UiEvent>(extraBufferCapacity = 15)

    private val userInfo = PlayMocker.getMockUserInfo()

    init {
        loadContent()

        mockChat()
        mockTotalViews()
    }

    private fun loadContent() {
        viewModelScope.launch {
            val contentInfo = PlayMocker.getMockContentInfo()
            _contentInfo.emit(PlayMapper.getContentInfo(contentInfo))
            _totalView.emit(PlayMapper.getTotalView(contentInfo))

            startVideo(contentInfo.videoUrl)
        }
    }

    fun dispatchAction(action: UiAction) {
        when (action) {
            is SendChat -> sendChat(action.message)
        }
    }

    private fun sendChat(message: String) {
        viewModelScope.launch {
            // TODO("send chat to the server")
            onRetrieveChat(
                ChatUiModel(
                    sender = userInfo.username,
                    message = message
                )
            )
        }
    }

    private fun onRetrieveChat(chat: ChatUiModel) {
        _uiEvent.tryEmit(ShowRealTimeChat(chat))
    }

    private fun onRetrieveTotalViews(totalView: Long) {
        _totalView.value = TotalViewUiModel(totalView)
    }

    private fun startVideo(videoUrl: String) {
        videoManager.play(videoUrl)
        videoManager.setRepeatMode(true)
    }

    override fun onCleared() {
        super.onCleared()
        videoManager.stop()
    }

    private fun mockChat() {
        viewModelScope.launch {
            delay(2000)
            while(isActive) {
                delay(2000)
                onRetrieveChat(PlayMocker.getMockChat())
            }
        }
    }

    private fun mockTotalViews() {
        viewModelScope.launch {
            delay(3000)
            while(isActive) {
                delay(1000)
                onRetrieveTotalViews(
                    PlayMocker.getMockTotalView(
                        currentTotalView = _totalView.value.totalView
                    )
                )
            }
        }
    }
}