package com.mirellageri.searchmovies.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.jakewharton.picasso.OkHttp3Downloader
import com.mirellageri.searchmovies.R
import com.mirellageri.searchmovies.data.Movie
import com.mirellageri.searchmovies.data.MoviesApi
import com.mirellageri.searchmovies.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.squareup.picasso.Callback
import okhttp3.OkHttpClient
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_MOVIE_IMDBID = "MOVIE_ID"
    }
    lateinit var binding: ActivityDetailBinding
    lateinit var movie : Movie
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val id = intent.getStringExtra(EXTRA_MOVIE_IMDBID)!!

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val api = MoviesApi.getInstance()
                    movie = api.getMovieById(id)
                    CoroutineScope(Dispatchers.Main).launch {
                        loadData()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun loadData(){
        supportActionBar?.title = movie.title
        binding.titleTextView.text = movie.title
        binding.yearChip.text = movie.year
        binding.typeTextView.text = movie.type

        binding.synopsisTextView.text = movie.plot
        binding.runtimeTextView.text = movie.runtime
        binding.directorTextView.text = movie.director
        binding.countryTextView.text = movie.country
        binding.genreTextView.text = movie.genre

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
    }
}