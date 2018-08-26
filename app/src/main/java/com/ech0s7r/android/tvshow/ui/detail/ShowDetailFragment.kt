package com.ech0s7r.android.tvshow.ui.detail

import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ech0s7r.android.base.ui.base.BaseActivity
import com.ech0s7r.android.base.ui.base.BaseFragment
import com.ech0s7r.android.base.utils.ImgUtils
import com.ech0s7r.android.base.utils.lifecycle.observeK
import com.ech0s7r.android.tvshow.R
import com.ech0s7r.android.tvshow.model.Show
import com.ech0s7r.android.tvshow.remote.api.RestAPI
import com.ech0s7r.android.tvshow.ui.MainViewModel
import com.ech0s7r.android.tvshow.ui.common.ShowPagedListAdapter
import kotlinx.android.synthetic.main.fragment_show_details.*
import javax.inject.Inject

/**
 * @author ech0s7r
 */
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
            similarShow.observeK(viewLifecycleOwner, ::similarShowChanged)
        }
    }

    private fun setupRecyclerView() {
        adapter = SimilarPagedListAdapter(requireActivity(), ::onSimilarShowSelected)
        list_similar_show.adapter = adapter
        list_similar_show.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
    }

    private fun onSimilarShowSelected(show: Show?) {
        show?.let { viewModel.setSelectedShow(it) }
    }

    private fun setupActionBar(actionBar: ActionBar?) {
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun similarShowChanged(pagedList: PagedList<Show>?) {
        pagedList?.let {
            adapter.submitList(it)
        }
    }

    private fun fillShowDetails(show: Show?) {
        //Logger.i("selected show: $show")
        main_container.smoothScrollTo(0, 0)
        show?.let {
            requireActivity().title = it.name
            title.text = it.name
            ImgUtils.load(requireActivity(), RestAPI.IMG_LARGE_BASE_PATH + it.poster_path, poster)
            poster.alpha = 0.5f
            year.text = it.first_air_date.substringBefore("-")
            companies.text = it.origin_country.joinToString(", ")
            overview.text = it.overview
        }
    }

}