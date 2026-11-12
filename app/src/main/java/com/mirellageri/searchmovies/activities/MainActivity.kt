package com.mirellageri.searchmovies.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mirellageri.searchmovies.R
import com.mirellageri.searchmovies.adapters.MovieAdapter
import com.mirellageri.searchmovies.data.Movie
import com.mirellageri.searchmovies.data.MoviesApi
import com.mirellageri.searchmovies.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MovieAdapter

    var movieList : List<Movie> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        adapter = MovieAdapter(movieList){ position ->
            val movie = movieList[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE_IMDBID, movie.imdbID)
            startActivity(intent)
        }
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.saveButton.setOnClickListener {
            val search = binding.titleEditText.editText?.text.toString()
            if (search.isNotBlank()){
                getMovieApi(search)
            }else{
                Toast.makeText(this,"Ingresar nombre de pelicula correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun getMovieApi(search:String){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val api = MoviesApi.getInstance()
                movieList = api.getMoviesList(search).search

                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateData(movieList)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}