package com.coderbyte.moviemania.ui.home

import android.os.Bundle
import android.view.View
import com.coderbyte.moviemania.R
import com.coderbyte.moviemania.base.fragment.BaseFragment
import com.coderbyte.moviemania.databinding.FragmentHomeBinding
import com.coderbyte.moviemania.utils.EqualSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.dimen

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private var homeAdapter: HomeAdapter? = null
    private val itemDecoration by lazy {
        EqualSpacingItemDecoration(
            requireContext().dimen(
                R.dimen._9sdp
            ), EqualSpacingItemDecoration.HORIZONTAL
        )
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    override fun setVariables(dataBinding: FragmentHomeBinding) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel?.getPopularMovies(1)
        viewModel?.getPopularTvSeries(1)
        viewModel?.getTrending(1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iniView()
        initObserver()
    }

    private fun iniView() {
        homeAdapter = HomeAdapter(requireContext())
        rvHome?.removeItemDecoration(itemDecoration)
        rvHome?.addItemDecoration(itemDecoration)
        rvHome?.adapter = homeAdapter
    }

    private fun initObserver() {
        viewModel?.popularMoviesResponse?.observe(viewLifecycleOwner,{
            it?.popularMovies?.let {list->
                homeAdapter?.addItems(HomeAdapter.TYPE_POPULAR_MOVIE,list)
            }
        })

        viewModel?.popularTvSeriesResponse?.observe(viewLifecycleOwner,{
            it.popularTvSeries?.let {list->
                homeAdapter?.addItems(HomeAdapter.TYPE_POPULAR_TV_SERIES,list)
            }
        })

        viewModel?.popularTrendingResponse?.observe(viewLifecycleOwner,{
            it.trendingContent?.let {list->
                homeAdapter?.addItems(HomeAdapter.TYPE_TRENDING,list)
            }
        })
    }
}