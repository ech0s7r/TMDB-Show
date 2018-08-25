package com.ech0s7r.android.tvshow

import android.os.Bundle
import android.view.View
import com.ech0s7r.android.base.ui.base.BaseActivity
import com.ech0s7r.android.tvshow.ui.popular.PopularFragment
import com.ech0s7r.android.base.utils.gotoFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author ech0s7r
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            gotoFragment<PopularFragment>(container.id)
        }
    }

}




