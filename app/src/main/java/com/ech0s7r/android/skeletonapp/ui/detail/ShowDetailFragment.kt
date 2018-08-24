package com.ech0s7r.android.skeletonapp.ui.detail

import android.arch.paging.PagedList
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ech0s7r.android.log.Logger
import com.ech0s7r.android.skeletonapp.R
import com.ech0s7r.android.skeletonapp.model.tv.Show
import com.ech0s7r.android.skeletonapp.remote.api.RestAPI
import com.ech0s7r.android.skeletonapp.ui.base.BaseActivity
import com.ech0s7r.android.skeletonapp.ui.base.BaseFragment
import com.ech0s7r.android.skeletonapp.ui.common.ShowPagedListAdapter
import com.ech0s7r.android.skeletonapp.utils.ImgUtils
import com.ech0s7r.android.skeletonapp.utils.lifecycle.observeK
import com.ech0s7r.android.skeletonapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_show_details.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


class ShowDetailFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var adapter: ShowPagedListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupActionBar((requireActivity() as BaseActivity).supportActionBar)
        setupRecyclerView()
        with(viewModel) {
            selectedShow.observeK(viewLifecycleOwner, ::fillShowDetails)

            popularShow.observeK(viewLifecycleOwner, ::popularShowChanged)
        }
    }

    private fun setupRecyclerView() {
        adapter = SimilarPagedListAdapter(requireActivity())
        list_similar_show.adapter = adapter
        list_similar_show.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)

//        val snapHelper = PagerSnapHelper()
//        snapHelper.attachToRecyclerView(list_similar_show)
    }

    private fun setupActionBar(actionBar: ActionBar?) {
        actionBar?.setDisplayHomeAsUpEnabled(true)
        // disable collapsing
        (requireActivity().toolbar.layoutParams as AppBarLayout.LayoutParams)
                .scrollFlags = 0
    }

    private fun popularShowChanged(pagedList: PagedList<Show>?) {
        pagedList?.let {
            adapter.submitList(it)
        }
    }

    private fun fillShowDetails(show: Show?) {
        Logger.i("selected show: $show")
        show?.let {
            requireActivity().title = it.name
            title.text = it.name
            ImgUtils.load(requireActivity(), RestAPI.IMG_LARGE_BASE_PATH + it.poster_path, poster)
            year.text = it.first_air_date.substringBefore("-")
            companies.text = it.origin_country.joinToString(",")
            overview.text = it.overview
        }
    }

}