package com.ech0s7r.android.skeletonapp.ui.detail

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.ActionBar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ech0s7r.android.log.Logger
import com.ech0s7r.android.skeletonapp.R
import com.ech0s7r.android.skeletonapp.model.tv.Show
import com.ech0s7r.android.skeletonapp.remote.api.RestAPI
import com.ech0s7r.android.skeletonapp.ui.base.BaseActivity
import com.ech0s7r.android.skeletonapp.ui.base.BaseFragment
import com.ech0s7r.android.skeletonapp.utils.ImgUtils
import com.ech0s7r.android.skeletonapp.utils.lifecycle.observeK
import com.ech0s7r.android.skeletonapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_show_details.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class ShowDetailFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupActionBar((requireActivity() as BaseActivity).supportActionBar)
        with(viewModel) {
            selectedShow.observeK(viewLifecycleOwner, ::fillShowDetails)
        }
    }

    private fun setupActionBar(actionBar: ActionBar?) {
        actionBar?.setDisplayHomeAsUpEnabled(true)
        // disable collapsing
        (requireActivity().toolbar.layoutParams as AppBarLayout.LayoutParams)
                .scrollFlags = 0
    }

    private fun fillShowDetails(show: Show?) {
        Logger.i("selected show: $show")
        requireActivity().title = show?.name
        ImgUtils.load(requireActivity(), RestAPI.IMG_LARGE_BASE_PATH + show?.poster_path.toString(), poster)
    }

}