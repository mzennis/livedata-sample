package com.tokopedia.play.view.component

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes

/**
 * Created by jegul on 09/06/20
 */
abstract class PartialView(
        container: ViewGroup,
        @IdRes rootId: Int
) {

    protected val rootView = container.findViewById<View>(rootId)

    protected fun<T: View> findViewById(@IdRes id: Int): View = rootView.findViewById<T>(id)
}