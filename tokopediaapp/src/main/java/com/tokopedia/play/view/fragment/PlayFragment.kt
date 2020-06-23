package com.tokopedia.play.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.tokopedia.play.R
import com.tokopedia.play.view.viewmodel.PlayViewModel
import com.tokopedia.play.view.viewmodel.ViewModelFactory
import javax.inject.Inject


/**
 * Created by mzennis on 15/06/20.
 */
class PlayFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory
): Fragment() {

    private lateinit var viewModel: PlayViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(PlayViewModel::class.java)
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPage()
        setupContent()
    }

    private fun setupContent() {
        viewModel.loadContent()
    }

    private fun setupPage() {
        val fragmentFactory = childFragmentManager.fragmentFactory
        childFragmentManager.beginTransaction()
            .replace(R.id.fl_video_container, getFragmentByClassName(fragmentFactory, PlayVideoFragment::class.java), VIDEO_FRAGMENT_TAG)
            .commit()
        childFragmentManager.beginTransaction()
            .replace(R.id.fl_interaction_container, getFragmentByClassName(fragmentFactory, PlayInteractionFragment::class.java), INTERACTION_FRAGMENT_TAG)
            .commit()
    }

    private fun getFragmentByClassName(fragmentFactory: FragmentFactory, fragmentClass: Class<out Fragment>): Fragment {
        return fragmentFactory.instantiate(requireContext().classLoader, fragmentClass.name)
    }

    companion object {
        private const val VIDEO_FRAGMENT_TAG = "fragment_video"
        private const val INTERACTION_FRAGMENT_TAG = "fragment_video"
    }

}