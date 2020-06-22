package com.tokopedia.play.di

import android.content.Context
import com.tokopedia.play.view.custom.PlayVideoManager
import dagger.Module
import dagger.Provides

@Module
class PlayModule(private val context: Context) {

    @Provides
    fun providePlayVideoManager(): PlayVideoManager = PlayVideoManager(context)
}