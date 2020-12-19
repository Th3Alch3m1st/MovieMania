package com.coderbyte.moviemania.ui.contentdetails

import android.os.Bundle
import android.view.View
import com.coderbyte.moviemania.BuildConfig
import com.coderbyte.moviemania.R
import com.coderbyte.moviemania.base.fragment.BaseFragment
import com.coderbyte.moviemania.base.glide.GlideApp
import com.coderbyte.moviemania.data.model.WishListItem
import com.coderbyte.moviemania.data.model.tvseries.GenresItem
import com.coderbyte.moviemania.data.model.tvseries.SpokenLanguagesItem
import com.coderbyte.moviemania.databinding.FragmentContentDetailsBinding
import com.coderbyte.moviemania.ui.home.HomeViewModel
import com.coderbyte.moviemania.utils.getAppDateFormat
import com.coderbyte.moviemania.utils.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_content_details.*
import java.util.*

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
class ContentDetailsFragment : BaseFragment<HomeViewModel, FragmentContentDetailsBinding>() {
    companion object {
        const val ARG_CONTENT_ID = "ARG_CONTENT_ID"
        const val ARG_IS_MOVIE = "ARG_IS_MOVIE"
    }

    private lateinit var contentId: String
    var isMovie: Boolean = false
    override val layoutResourceId: Int
        get() = R.layout.fragment_content_details

    override fun setVariables(dataBinding: FragmentContentDetailsBinding) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentId = arguments?.getString(ARG_CONTENT_ID) ?: ""
        isMovie = arguments?.getBoolean(ARG_IS_MOVIE, false) ?: false
        if (isMovie) {
            viewModel?.getMovieDetails(contentId)
        } else {
            viewModel?.getSeriesDetails(contentId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()

        initView()
    }

    private fun initView() {
        addToWish?.setSafeOnClickListener {
            viewModel?.addOrRemoveWishListItem(WishListItem(contentId, ""))
        }

        val item = viewModel?.getWishListItem()?.find { it.id == contentId }
        item?.let {
            addToWish?.isChecked = true
        } ?: run {
            addToWish?.isChecked = false
        }
    }

    private fun initObserver() {
        viewModel?.moviewDetailsResponse?.observe(viewLifecycleOwner, {
            GlideApp.with(requireContext()).load(BuildConfig.POSTER_IMAGE_BASE_URL + it.posterPath)
                .placeholder(R.drawable.image_banner_placeholder)
                .error(R.drawable.image_banner_placeholder)
                .into(ivMovieImage)
            tvMovieTitle?.text = it.title
            tvgenres?.text = "Genre: " + getGenre(it.genres)
            tvLanguage?.text = "Language: " + getLanguage(it.spokenLanguages)
            tvReleaseDate?.text = "Release Date: ${getAppDateFormat(it.releaseDate)}"
            tvRating?.text = "Rating: " + String.format(
                Locale.getDefault(),
                "%.1f",
                it.voteAverage
            ) + "(${it.voteCount})"
            tvOverview?.text = "Overview: " + it.overview ?: ""
        })

        viewModel?.tvSerieswDetailsResponse?.observe(viewLifecycleOwner, {
            GlideApp.with(requireContext()).load(BuildConfig.POSTER_IMAGE_BASE_URL + it.posterPath)
                .placeholder(R.drawable.image_banner_placeholder)
                .error(R.drawable.image_banner_placeholder)
                .into(ivMovieImage)
            tvMovieTitle?.text = it.name
            tvgenres?.text = "Genre: " + getGenre(it.genres)
            tvLanguage?.text = "Language: " + getLanguage(it.spokenLanguages)
            tvReleaseDate?.text = "Release Date: ${getAppDateFormat(it.firstAirDate)}"
            tvRating?.text = "Rating: " + String.format(
                Locale.getDefault(),
                "%.1f",
                it.voteAverage
            ) + "(${it.voteCount})"
            tvOverview?.text = "Overview: " + it.overview ?: ""
        })
    }

    private fun getLanguage(spokenLanguages: List<SpokenLanguagesItem>?): CharSequence? {
        var language = ""
        for (i in 0 until (spokenLanguages?.size ?: 0)) {
            if (i > 0) {
                language += ", "
            }
            language += spokenLanguages?.get(i)?.englishName ?: ""
        }

        return language
    }

    private fun getGenre(genres: List<GenresItem>?): String {
        var genre = ""
        for (i in 0 until (genres?.size ?: 0)) {
            if (i > 0) {
                genre += ", "
            }
            genre += genres?.get(i)?.name ?: ""
        }

        return genre
    }


}