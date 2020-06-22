package com.tokopedia.play.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.SimpleExoPlayer
import com.tokopedia.play.data.PlayMapper
import com.tokopedia.play.data.PlayMocker
import com.tokopedia.play.view.custom.PlayVideoManager
import com.tokopedia.play.view.uimodel.*
import kotlinx.coroutines.*
import javax.inject.Inject


/**
 * Created by mzennis on 15/06/20.
 */
class PlayViewModel @Inject constructor(private val videoManager: PlayVideoManager): ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val observableContentInfo: LiveData<ContentInfoUiModel>
        get() = _observableContentInfo
    private val _observableContentInfo = MutableLiveData<ContentInfoUiModel>()

    val observableTotalView: LiveData<TotalViewUiModel> get() = _observableTotalView
    private val _observableTotalView = MutableLiveData<TotalViewUiModel>()

    val observableVideoPlayer: LiveData<SimpleExoPlayer> get() = videoManager.getVideoPlayer()

    val observableVideoProperty: LiveData<VideoPropertyUiModel> get() = _observableVideoProperty
    private val _observableVideoProperty: MediatorLiveData<VideoPropertyUiModel> = MediatorLiveData<VideoPropertyUiModel>().apply {
        addSource(videoManager.getVideoState()) { videoState ->
            value = VideoPropertyUiModel(videoState)
        }
    }

    val observableNewChat: LiveData<ChatUiModel> get() = _observableNewChat
    private val _observableNewChat = MutableLiveData<ChatUiModel>()

    private val userInfo = PlayMocker.getMockUserInfo()

    init {
        mockChat()
        mockTotalViews()
    }

    fun loadContent() {
        uiScope.launch {
            val contentInfo = PlayMocker.getMockContentInfo()

            startVideo(contentInfo.videoUrl)

            _observableContentInfo.value = PlayMapper.getContentInfo(contentInfo)
            _observableTotalView.value = PlayMapper.getTotalView(contentInfo)
        }
    }

    private fun startVideo(videoUrl: String) {
        videoManager.play(videoUrl)
        videoManager.setRepeatMode(true)
    }

    fun sendChat(message: String) {
        uiScope.launch {
            // TODO("send chat to the server")
            onRetrieveChat(
                ChatUiModel(
                    sender = userInfo.username,
                    message = message
                )
            )
        }
    }

    private fun onRetrieveChat(chatUiModel: ChatUiModel) {
        _observableNewChat.value = chatUiModel
    }

    private fun onRetrieveTotalViews(totalView: Long) {
        _observableTotalView.value = TotalViewUiModel(totalView)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun mockChat() {
        uiScope.launch {
            delay(2000)
            while(isActive) {
                delay(2000)
                onRetrieveChat(PlayMocker.getMockChat())
            }
        }
    }

    private fun mockTotalViews() {
        uiScope.launch {
            delay(3000)
            while(isActive) {
                delay(1000)
                onRetrieveTotalViews(
                    PlayMocker.getMockTotalView(
                        currentTotalView = _observableTotalView.value?.totalView?:0L
                    )
                )
            }
        }
    }
}