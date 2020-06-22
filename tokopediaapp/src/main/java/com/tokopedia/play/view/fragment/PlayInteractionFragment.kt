package com.tokopedia.play.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tokopedia.play.R
import com.tokopedia.play.view.component.PlayActionBarView
import com.tokopedia.play.view.component.PlayChatFormView
import com.tokopedia.play.view.component.PlayChatListView
import com.tokopedia.play.view.component.PlayStatInfoView
import com.tokopedia.play.view.viewmodel.PlayViewModel
import com.tokopedia.play.view.viewmodel.ViewModelFactory
import javax.inject.Inject


/**
 * Created by mzennis on 15/06/20.
 */
class PlayInteractionFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory
): Fragment() {

    private lateinit var viewModel: PlayViewModel

    private lateinit var actionBarView: PlayActionBarView
    private lateinit var statInfoView: PlayStatInfoView
    private lateinit var chatListView: PlayChatListView
    private lateinit var chatFormView: PlayChatFormView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(PlayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play_interaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)

        observeContentInfo()
        observeTotalView()
        observeChatList()
    }

    private fun initView(view: View) {
        with(view) {
            actionBarView = PlayActionBarView(this as ViewGroup, object : PlayActionBarView.Listener{
                override fun onBackIconClicked() {
                    requireActivity().onBackPressed()
                }
            })
            statInfoView = PlayStatInfoView(this as ViewGroup)
            chatListView = PlayChatListView(this as ViewGroup)
            chatFormView = PlayChatFormView(this as ViewGroup, object : PlayChatFormView.Listener{
                override fun onSendClicked(message: String) {
                    doSendChat(message)
                }
            })
        }
    }

    private fun doSendChat(message: String) {
        viewModel.sendChat(message)
    }

    /**
     * Observer
     */
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
}