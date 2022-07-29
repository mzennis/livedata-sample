package com.tokopedia.play.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.tokopedia.play.di.key.FragmentKey
import com.tokopedia.play.view.fragment.PlayFragment
import com.tokopedia.play.view.fragment.factory.PlayFragmentFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityComponent::class)
abstract class PlayFragmentModule {

    @Binds
    abstract fun bindFragmentFactory(fragmentFactory: PlayFragmentFactory): FragmentFactory

    @Binds
    @IntoMap
    @FragmentKey(PlayFragment::class)
    abstract fun getPlayFragment(fragment: PlayFragment): Fragment
}