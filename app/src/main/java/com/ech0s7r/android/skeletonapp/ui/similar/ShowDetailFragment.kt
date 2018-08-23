package com.ech0s7r.android.skeletonapp.ui.similar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ech0s7r.android.skeletonapp.R
import com.ech0s7r.android.skeletonapp.model.tv.Show
import com.ech0s7r.android.skeletonapp.ui.base.BaseFragment
import com.ech0s7r.android.skeletonapp.utils.lifecycle.observeK
import com.ech0s7r.android.skeletonapp.viewmodel.MainViewModel
import javax.inject.Inject

class ShowDetailFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            selectedShow.observeK(viewLifecycleOwner, ::fillShowDetails)
        }
    }

    private fun fillShowDetails(show: Show?) {

    }

}