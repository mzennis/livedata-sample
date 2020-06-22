package com.tokopedia.play.di

import com.tokopedia.play.view.activity.PlayActivity
import dagger.Component

@Component(
        modules = [PlayViewModelModule::class,
            PlayModule::class,
            PlayFragmentModule::class]
)
@PlayScope
interface PlayComponent {

    fun inject(playActivity: PlayActivity)
}