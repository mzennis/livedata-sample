package com.tokopedia.play.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.tokopedia.play.R
import com.tokopedia.play.view.fragment.PlayFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlayActivity : AppCompatActivity() {

    @Inject lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        setFragmentFactory()
        setupPage()
    }
    private fun setFragmentFactory() {
        supportFragmentManager.fragmentFactory = fragmentFactory
    }

    private fun getFragmentByClassName(fragmentClass: Class<out Fragment>): Fragment {
        return fragmentFactory.instantiate(classLoader, fragmentClass.name)
    }

    private fun setupPage() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_container, getFragmentByClassName(PlayFragment::class.java), PLAY_FRAGMENT_TAG)
            .commit()
    }

    companion object {
        private const val PLAY_FRAGMENT_TAG = "FRAGMENT_PLAY"
    }
}