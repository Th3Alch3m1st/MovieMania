package com.coderbyte.moviemania.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coderbyte.moviemania.R
import com.coderbyte.moviemania.data.model.movie.PopularMovie
import com.coderbyte.moviemania.utils.getAppDateFormat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.single_item_movies.*

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
class PopularMoviesAdapter(private val movies: MutableList<PopularMovie>): RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewHolder>(){
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.single_item_movies, parent, false)
        return PopularMoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        val movie = movies[position]
        holder.tvMovieTitle?.text = movie.title ?: ""
        holder.tvReleaseDate?.text = getAppDateFormat(movie.releaseDate)
        holder.tvPosterPath?.text = movie.posterPath ?: "No path found"
        holder.tvVoteCount?.text = movie.voteCount?.toString() ?: ""
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class PopularMoviesViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = itemView
    }
}