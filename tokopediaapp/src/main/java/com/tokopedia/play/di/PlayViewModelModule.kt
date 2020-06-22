package com.tokopedia.play.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tokopedia.play.view.viewmodel.PlayViewModel
import com.tokopedia.play.view.viewmodel.ViewModelFactory
import com.tokopedia.play.view.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PlayViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PlayViewModel::class)
    abstract fun getPlayViewModel(viewModel: PlayViewModel): ViewModel

}