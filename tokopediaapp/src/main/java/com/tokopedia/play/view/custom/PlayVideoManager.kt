package com.tokopedia.play.view.custom

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy
import com.google.android.exoplayer2.upstream.cache.*
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

/**
 * Created by mzennis on 16/06/20.
 */
class PlayVideoManager @Inject constructor(@ApplicationContext private val context: Context) {

    private lateinit var videoPlayer: SimpleExoPlayer
    private lateinit var cache: Cache

    private val _observableVideoPlayer = MutableLiveData<SimpleExoPlayer>()
    private val _observablePlayVideoState = MutableLiveData<VideoState>()

    private val cacheFile: File
        get() = File(context.filesDir, CACHE_FOLDER_NAME)
    private val cacheEvictor: CacheEvictor
        get() = LeastRecentlyUsedCacheEvictor(MAX_CACHE_BYTES)
    private val cacheDbProvider: DatabaseProvider
        get() = ExoDatabaseProvider(context)

    private val playerEventListener = object : Player.EventListener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            when (playbackState) {
                Player.STATE_IDLE -> _observablePlayVideoState.value = VideoState.NoMedia
                Player.STATE_BUFFERING -> _observablePlayVideoState.value = VideoState.Buffering
                Player.STATE_READY -> {
                    _observablePlayVideoState.value = if (!playWhenReady) VideoState.Pause else VideoState.Playing
                }
                Player.STATE_ENDED -> _observablePlayVideoState.value = VideoState.Ended
            }
        }
    }

    private fun initVideoPlayer(): SimpleExoPlayer  {
        if (this::videoPlayer.isInitialized) return videoPlayer
        videoPlayer = SimpleExoPlayer.Builder(context)
            .build()
            .apply { addListener(playerEventListener) }
        return videoPlayer
    }

    private fun initCache(): Cache {
        if (this::cache.isInitialized) return cache
        cache = SimpleCache(
            cacheFile,
            cacheEvictor,
            cacheDbProvider
        )
        return cache
    }

    fun play(url: String) {
        _observableVideoPlayer.value = initVideoPlayer()
        val mediaSource = getMediaSourceBySource(context,  Uri.parse(url))
        videoPlayer.playWhenReady = true
        videoPlayer.prepare(mediaSource)
    }

    fun stop() {
        videoPlayer.stop()
        cache.release()
    }

    fun setRepeatMode(shouldRepeat: Boolean) {
        videoPlayer.repeatMode = if(shouldRepeat) Player.REPEAT_MODE_ALL else Player.REPEAT_MODE_OFF
    }

    fun getVideoState(): LiveData<VideoState> = _observablePlayVideoState
    fun getVideoPlayer(): LiveData<SimpleExoPlayer> = _observableVideoPlayer

    private fun getMediaSourceBySource(context: Context, uri: Uri): MediaSource {
        val mDataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "Example"))
        val cacheDataSourceFactory = CacheDataSourceFactory(initCache(), mDataSourceFactory)
        val errorHandlingPolicy = DefaultLoadErrorHandlingPolicy()
        val mediaSource = ProgressiveMediaSource.Factory(cacheDataSourceFactory).setLoadErrorHandlingPolicy(errorHandlingPolicy)
        return mediaSource.createMediaSource(uri)
    }

    companion object {
        private const val MAX_CACHE_BYTES: Long = 10 * 1024 * 1024
        private const val CACHE_FOLDER_NAME = "play_video"
    }
}