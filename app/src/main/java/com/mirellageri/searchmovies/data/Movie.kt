package com.mirellageri.searchmovies.data

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Type") val type: String,
    @SerializedName("imdbID") val imdbID : String,
    @SerializedName("Poster") val thumbnail: String,
    @SerializedName("Plot") val plot : String,
    @SerializedName("Country") val country : String,
    @SerializedName("Runtime") val runtime : String,
    @SerializedName("Director") val director : String,
    @SerializedName("Genre") val genre : String
)

data class MovieSearchResponse(
    @SerializedName("Search") val search: List<Movie>
)
