package com.mirellageri.searchmovies.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("/")
    suspend fun getMoviesList(@Query("s")search: String,
                              @Query("apikey")apikey: String = API_KEY) : MovieSearchResponse
    @GET("/")
    suspend fun getMovieByTitle(@Query("t")title: String,
                                @Query("apikey")apikey: String = API_KEY): Movie
    @GET("/")
    suspend fun getMovieById(@Query("i")id: String,
                             @Query("apikey")apikey: String = API_KEY): Movie

    companion object{
        private const val API_KEY = "f8b56a74"
        fun getInstance(): MoviesApi{
            val retrofit= Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            //le pasamos los planos de la clase y nos devuelve un objeto nuevo
            return retrofit.create(MoviesApi::class.java)
        }
    }
}