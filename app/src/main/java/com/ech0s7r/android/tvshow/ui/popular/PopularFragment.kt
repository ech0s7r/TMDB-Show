package com.ech0s7r.android.tvshow.ui.popular

import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ech0s7r.android.base.ui.base.BaseActivity
import com.ech0s7r.android.base.ui.base.BaseFragment
import com.ech0s7r.android.base.utils.gotoFragment
import com.ech0s7r.android.base.utils.lifecycle.observeK
import com.ech0s7r.android.tvshow.R
import com.ech0s7r.android.tvshow.data.ShowDataSource
import com.ech0s7r.android.tvshow.model.Show
import com.ech0s7r.android.tvshow.ui.MainViewModel
import com.ech0s7r.android.tvshow.ui.common.ShowPagedListAdapter
import com.ech0s7r.android.tvshow.ui.detail.ShowDetailFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_popular.*
import javax.inject.Inject


/**
 *
 * @author ech0s7r
 */
class PopularFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var adapter: ShowPagedListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_popular, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().title = getString(R.string.app_name)
        setupActionBar((requireActivity() as BaseActivity).supportActionBar)
        adapter = PopularPagedListAdapter(requireActivity(), ::onShowSelected)
        setupRecyclerView(list_view)
        with(viewModel) {
            popularShow.observeK(viewLifecycleOwner, ::popularShowChanged)
            refreshState.observeK(viewLifecycleOwner, ::isRefreshing)
            networkState.observeK(viewLifecycleOwner, adapter::onNetworkStateChange)
            swipe_refresh.setOnRefreshListener { refreshPopularShow() }
        }
    }

    private fun setupActionBar(actionBar: ActionBar?) {
        actionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setupRecyclerView(listView: RecyclerView) {
        listView.adapter = adapter
        listView.layoutManager = GridLayoutManager(requireContext(), 3)
    }

    private fun popularShowChanged(pagedList: PagedList<Show>?) {
        pagedList?.let {
            adapter.submitList(it)
        }
    }

    private fun isRefreshing(networkState: ShowDataSource.NetworkState?) {
        swipe_refresh.isRefreshing = networkState == ShowDataSource.NetworkState.LOADING
    }

    private fun onShowSelected(selectedShow: Show?) {
        selectedShow?.let { show ->
            viewModel.setSelectedShow(show)
            gotoFragment<ShowDetailFragment>(requireActivity().container.id, true)
        }
    }

}
