package com.tokopedia.play.view.component

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.tokopedia.play.R
import com.tokopedia.play.view.uimodel.TotalViewUiModel


/**
 * Created by mzennis on 16/06/20.
 */
class PlayStatInfoView(
    container: ViewGroup
) : PartialView(container, R.id.layout_stat_info){

    private var tvLiveBadge: AppCompatTextView = container.findViewById(R.id.tv_live_badge)
    private var tvTotalView: AppCompatTextView = container.findViewById(R.id.tv_total_views)

    fun shouldShowBadgeLive(shouldShow: Boolean) {
        if (shouldShow) tvLiveBadge.visibility = View.VISIBLE
        else View.GONE
    }

    fun setTotalView(totalViewUiModel: TotalViewUiModel) {
        tvTotalView.text = totalViewUiModel.totalView.toString()
    }
}