package com.tokopedia.play.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.tokopedia.play.R
import com.tokopedia.play.view.component.*
import com.tokopedia.play.view.uimodel.VideoState
import com.tokopedia.play.view.viewmodel.PlayViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * Created by mzennis on 15/06/20.
 */
@AndroidEntryPoint
class PlayFragment @Inject constructor(): Fragment() {

    private val viewModel: PlayViewModel by activityViewModels()

    private lateinit var playerView: PlayerView
    private lateinit var playerLoadingView: PlayLoadingView

    private lateinit var actionBarView: PlayActionBarView
    private lateinit var statInfoView: PlayStatInfoView
    private lateinit var chatListView: PlayChatListView
    private lateinit var chatFormView: PlayChatFormView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)

        observeVideoProperty()
        observeVideoPlayer()
        observeContentInfo()
        observeTotalView()
        observeChatList()

        setupContent()
    }

    private fun setupContent() {
        viewModel.loadContent()
    }

    private fun initView(view: View) {
        with(view) {
            playerView = findViewById(R.id.exo_player_view)
            playerLoadingView  = PlayLoadingView(this as ViewGroup)
            actionBarView = PlayActionBarView(this, object : PlayActionBarView.Listener{
                override fun onBackIconClicked() {
                    requireActivity().onBackPressed()
                }
            })
            statInfoView = PlayStatInfoView(this)
            chatListView = PlayChatListView(this)
            chatFormView = PlayChatFormView(this, object : PlayChatFormView.Listener{
                override fun onSendClicked(message: String) {
                    doSendChat(message)
                }
            })
        }
    }

    private fun setVideoState(videoState: VideoState) {
        playerLoadingView.setVideoState(videoState)
    }

    private fun observeVideoProperty() {
        viewModel.observableVideoProperty.observe(viewLifecycleOwner, Observer {
            setVideoState(it.videoState)
        })
    }

    private fun observeVideoPlayer() {
        viewModel.observableVideoPlayer.observe(viewLifecycleOwner, Observer(this::setupPlayer))
    }
    private fun doSendChat(message: String) {
        viewModel.sendChat(message)
    }

    private fun observeContentInfo() {
        viewModel.observableContentInfo.observe(viewLifecycleOwner, Observer {
            actionBarView.setTitle(it.title)
            statInfoView.shouldShowBadgeLive(it.isLive)
        })
    }

    private fun observeTotalView() {
        viewModel.observableTotalView.observe(viewLifecycleOwner, Observer(statInfoView::setTotalView))
    }

    private fun observeChatList() {
        viewModel.observableNewChat.observe(viewLifecycleOwner, Observer(chatListView::addChat))
    }

    private fun setupPlayer(player: SimpleExoPlayer) {
        playerView.player = player
    }
}