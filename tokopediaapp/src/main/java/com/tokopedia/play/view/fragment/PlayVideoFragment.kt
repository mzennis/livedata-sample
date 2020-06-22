package com.tokopedia.play.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.tokopedia.play.R
import com.tokopedia.play.view.component.PlayLoadingView
import com.tokopedia.play.view.uimodel.VideoState
import com.tokopedia.play.view.viewmodel.PlayViewModel
import com.tokopedia.play.view.viewmodel.ViewModelFactory
import javax.inject.Inject

/**
 * Created by mzennis on 15/06/20.
 */
class PlayVideoFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory
): Fragment() {

    private lateinit var viewModel: PlayViewModel

    private lateinit var loadingView: PlayLoadingView
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(PlayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)

        observeVideoProperty()
        observeVideoPlayer()
    }

    private fun initView(view: View) {
        with(view) {
            playerView = findViewById(R.id.exo_player_view)
            loadingView  = PlayLoadingView(this as ViewGroup)
        }
    }

    private fun setupPlayer(player: SimpleExoPlayer) {
        playerView.player = player
    }

    private fun setVideoState(videoState: VideoState) {
        loadingView.setVideoState(videoState)
    }

    private fun observeVideoProperty() {
        viewModel.observableVideoProperty.observe(viewLifecycleOwner, Observer {
            setVideoState(it.videoState)
        })
    }

    private fun observeVideoPlayer() {
        viewModel.observableVideoPlayer.observe(viewLifecycleOwner, Observer(this::setupPlayer))
    }
}