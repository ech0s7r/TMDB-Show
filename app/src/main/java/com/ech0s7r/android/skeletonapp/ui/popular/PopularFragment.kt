package com.ech0s7r.android.skeletonapp.ui.popular

import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ech0s7r.android.skeletonapp.R
import com.ech0s7r.android.skeletonapp.model.tv.Show
import com.ech0s7r.android.skeletonapp.repository.ShowDataSource
import com.ech0s7r.android.skeletonapp.ui.base.BaseFragment
import com.ech0s7r.android.skeletonapp.ui.similar.ShowDetailFragment
import com.ech0s7r.android.skeletonapp.utils.gotoFragment
import com.ech0s7r.android.skeletonapp.utils.lifecycle.observeK
import com.ech0s7r.android.skeletonapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_popular.*
import javax.inject.Inject


/**
 *
 * @author ech0s7r
 */

class PopularFragment : BaseFragment() {

    companion object {
        /**
         * Span count to use with grid layout
         */
        const val gridSpanCount: Int = 3
    }

    @Inject
    lateinit var viewModel: MainViewModel

    lateinit var adapter: PopularListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_popular, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = PopularListAdapter(requireActivity(), ::onShowSelected)
        setupRecyclerView(list_view)
        with(viewModel) {
            showPopular()
            popularShow.observeK(viewLifecycleOwner, ::popularShowChanged)
            refreshState.observeK(viewLifecycleOwner, ::isRefreshing)
            networkState.observeK(viewLifecycleOwner, adapter::onNetworkStateChange)
            swipe_refresh.setOnRefreshListener { refreshPopularShow() }
        }
    }

    private fun setupRecyclerView(listView: RecyclerView) {
        listView.adapter = adapter
        //listView.layoutManager = GridLayoutManager(requireContext(), gridSpanCount)
        listView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(listView)
    }

    private fun popularShowChanged(pagedList: PagedList<Show>?) {
        pagedList?.let {
            adapter.submitList(it)
        }
    }

    private fun isRefreshing(networkState: ShowDataSource.NetworkState?) {
        swipe_refresh.isRefreshing = networkState == ShowDataSource.NetworkState.LOADING
    }

    private fun onShowSelected(selectedShow: Show?, view: View?) {
        selectedShow?.let { show ->
            viewModel.setSelectedShow(show)
            gotoFragment<ShowDetailFragment>(container.id, true)
        }
    }

}
