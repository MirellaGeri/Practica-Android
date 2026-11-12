package com.mirellageri.searchmovies.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirellageri.searchmovies.R
import com.mirellageri.searchmovies.data.Movie
import com.mirellageri.searchmovies.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.Callback

class MovieAdapter(var items: List<Movie>,
                   val onItemClick:(position : Int) -> Unit
): RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(layoutInflater,parent,false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = items[position]
        holder.render(movie)
        holder.binding.movieCard.setOnClickListener {
            onItemClick(position)
        }
    }

    override fun getItemCount(): Int = items.size
    fun updateData(dataSet: List<Movie>){
        this.items = dataSet
        notifyDataSetChanged()
    }
}
class MovieViewHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root){
    fun render(movie : Movie){
        binding.movieTitleTextView.text = movie.title
        binding.typeTextView.text = movie.type
        binding.yearChip.text = movie.year
        Log.i("API","${movie.thumbnail}")
        Picasso.get()
            .load(movie.thumbnail)
            .placeholder(R.drawable.bg_image_placeholder)
            .error(R.drawable.bg_image_placeholder)
            .into(binding.posterImageView, object : Callback {

                override fun onSuccess() {
                    Log.d("PICASSO", "Imagen cargada")
                }

                override fun onError(e: Exception?) {
                    Log.e("PICASSO", "Error: ", e)
                }
            })
        //binding.posterImageView.setImageResource(R.drawable.bg_image_placeholder)
    }
}