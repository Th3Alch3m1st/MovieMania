package com.coderbyte.moviemania.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coderbyte.moviemania.R
import com.coderbyte.moviemania.data.model.movie.PopularMovie
import com.coderbyte.moviemania.data.model.trending.TrendingContent
import com.coderbyte.moviemania.data.model.tvseries.PopularTvSeries
import com.coderbyte.moviemania.utils.EqualSpacingItemDecoration
import com.coderbyte.moviemania.utils.getAppDateFormat
import com.coderbyte.moviemania.utils.setSafeOnClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_popular_movies.*
import kotlinx.android.synthetic.main.single_item_movies.*
import org.jetbrains.anko.dimen
import org.jetbrains.anko.toast

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
class HomeAdapter(private val context: Context, private val homeItemCallBack: HomeItemCallBack) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_POPULAR_MOVIE = 0
        const val TYPE_POPULAR_TV_SERIES = 1
        const val TYPE_TRENDING = 2
    }

    private var homeItems = mutableListOf<Pair<Int, Any>>()

    private val itemDecoration by lazy {
        EqualSpacingItemDecoration(
            context.dimen(
                R.dimen._9sdp
            ), EqualSpacingItemDecoration.HORIZONTAL
        )
    }

    private lateinit var adapterPopularMovies: PopularMoviesAdapter
    private lateinit var adapterPopularTvSeries: PopularTvSeriesAdapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_POPULAR_MOVIE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_popular_movies, parent, false)
                PopularMovieViewHolder(view)
            }
            TYPE_POPULAR_TV_SERIES -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_popular_movies, parent, false)
                PopularTvSeriesViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.single_item_trending_content, parent, false)
                TrendingContentViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PopularMovieViewHolder -> {
                holder.tvTitle?.text = context.getString(R.string.popular_movies)
                if (!::adapterPopularMovies.isInitialized) {
                    adapterPopularMovies =
                        PopularMoviesAdapter(
                            homeItems[position].second as MutableList<PopularMovie>,
                            homeItemCallBack
                        )
                }
                holder.rvPopularMovies?.removeItemDecoration(itemDecoration)
                holder.rvPopularMovies?.addItemDecoration(itemDecoration)
                holder.rvPopularMovies.adapter = adapterPopularMovies

            }
            is PopularTvSeriesViewHolder -> {
                holder.tvTitle?.text = context.getString(R.string.popular_tv_series)
                if (!::adapterPopularTvSeries.isInitialized) {
                    adapterPopularTvSeries =
                        PopularTvSeriesAdapter(
                            homeItems[position].second as MutableList<PopularTvSeries>,
                            homeItemCallBack
                        )
                }
                holder.rvPopularMovies?.removeItemDecoration(itemDecoration)
                holder.rvPopularMovies?.addItemDecoration(itemDecoration)
                holder.rvPopularMovies.adapter = adapterPopularTvSeries
            }
            is TrendingContentViewHolder -> {
                val trendingList =
                    if (position > 2) homeItems[2].second as MutableList<TrendingContent> else homeItems[position].second as MutableList<TrendingContent>
                var count = 0
                if (homeItems.getOrNull(TYPE_POPULAR_MOVIE) != null) {
                    ++count
                }
                if (homeItems.getOrNull(TYPE_POPULAR_TV_SERIES) != null) {
                    ++count
                }
                if (position - count >= 0) {
                    val trendingItem = trendingList[position - count]

                    if (trendingItem.mediaType == TrendingContent.TYPE_MOVIE) {
                        holder.tvMovieTitle?.text = trendingItem.title ?: ""
                        holder.tvReleaseDate?.text = getAppDateFormat(trendingItem.releaseDate)
                        holder.itemView.setSafeOnClickListener {
                            trendingItem.id?.let {
                                homeItemCallBack.onMovieClick(it.toString())
                            } ?: run { context.toast("No Id found") }
                        }
                    } else {
                        holder.tvMovieTitle?.text = trendingItem.originalName ?: ""
                        holder.tvReleaseDate?.text = getAppDateFormat(trendingItem.releaseDate)
                        holder.itemView?.setSafeOnClickListener {
                            trendingItem.id?.let {
                                homeItemCallBack.onTvSeriesClick(it.toString())
                            } ?: run { context.toast("No Id found") }
                        }
                    }
                    holder.tvPosterPath?.text = trendingItem.posterPath ?: "No path found"
                    holder.tvVoteCount?.text = trendingItem.voteCount?.toString() ?: ""
                }else{
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position > 2) {
            return TYPE_TRENDING
        }
        return homeItems[position].first
    }

    override fun getItemCount(): Int {
        var count = 0

        if (homeItems.getOrNull(TYPE_POPULAR_MOVIE) != null) {
            ++count
        }
        if (homeItems.getOrNull(TYPE_POPULAR_TV_SERIES) != null) {
            ++count
        }
        if (homeItems.getOrNull(TYPE_TRENDING) != null) {
            count += (homeItems[TYPE_TRENDING].second as MutableList<*>).size
        }

        return count
    }

    fun addItems(itemType: Int, obj: Any) {
        var previousIndex = -1
        homeItems.forEach {
            if (it.first == itemType) {
                previousIndex = homeItems.indexOf(it)
            }
        }

        if (previousIndex == -1) {
            val pairObject = Pair(itemType, obj)
            homeItems.add(pairObject)
            homeItems.sortBy { it.first }
            val currentPosition = homeItems.indexOf(pairObject)
            notifyItemInserted(currentPosition)

        } else {
            homeItems[previousIndex] = Pair(itemType, obj)
            notifyItemChanged(previousIndex)
        }
    }

    class PopularMovieViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View
            get() = itemView

    }

    class PopularTvSeriesViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View
            get() = itemView

    }

    class TrendingContentViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View
            get() = itemView

    }

    interface HomeItemCallBack {
        fun onMovieClick(id: String)
        fun onTvSeriesClick(id: String)
    }
}