package com.ech0s7r.android.skeletonapp

import android.os.Bundle
import com.ech0s7r.android.skeletonapp.ui.base.BaseActivity
import com.ech0s7r.android.skeletonapp.ui.popular.PopularFragment
import com.ech0s7r.android.skeletonapp.utils.gotoFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author ech0s7r
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            gotoFragment<PopularFragment>(container.id)
        }

    }

}




