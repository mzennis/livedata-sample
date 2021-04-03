package com.tokopedia.play.di

import android.content.Context
import com.tokopedia.play.view.custom.PlayVideoManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
object PlayModule {

    @Provides
    fun providePlayVideoManager(@ApplicationContext context: Context): PlayVideoManager {
        return PlayVideoManager(context)
    }
}