package com.tokopedia.play.view.viewmodel

import androidx.lifecycle.*
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
import com.tokopedia.play.view.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by mzennis on 15/06/20.
 */
@HiltViewModel
class PlayViewModel @Inject constructor(
    private val videoManager: PlayVideoManager
): ViewModel() {

    val observableVideoPlayer: LiveData<SimpleExoPlayer> get() = videoManager.getVideoPlayer()

    private val _contentInfo = MutableLiveData<ContentInfoUiModel>()
    private val _totalView = MutableLiveData<TotalViewUiModel>()

    val uiState: LiveData<UiState>
        get() = _uiState
    private val _uiState: MediatorLiveData<UiState> = MediatorLiveData<UiState>().apply {
        value = UiState.Initial
        addSource(_contentInfo) { content ->
            value = value?.copy(contentInfo = content)
        }
        addSource(_totalView) { totalView ->
            value = value?.copy(totalView = totalView)
        }
        addSource(videoManager.getVideoState()) { videoState ->
            value = value?.copy(videoUiState = PlayMapper.getVideoState(videoState))
        }
    }

    val uiEvent: SingleLiveEvent<UiEvent>
        get() = _uiEvent
    private val _uiEvent = SingleLiveEvent<UiEvent>()

    private val userInfo = PlayMocker.getMockUserInfo()

    init {
        loadContent()

        mockChat()
        mockTotalViews()
    }

    private fun loadContent() {
        viewModelScope.launch {
            val contentInfo = PlayMocker.getMockContentInfo()
            _contentInfo.value = PlayMapper.getContentInfo(contentInfo)
            _totalView.value = PlayMapper.getTotalView(contentInfo)

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
        _uiEvent.value = ShowRealTimeChat(chat)
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
                        currentTotalView = _totalView.value?.totalView?:0L
                    )
                )
            }
        }
    }
}