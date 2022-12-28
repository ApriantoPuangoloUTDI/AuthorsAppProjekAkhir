package com.example.author

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("volumes")
    fun getBook(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int,
        @Query("printType") printType: String
    ): Call<BooksResponse>
}
//Berfungsi untuk mendeklarasikan semua yang akan kita lakukan misalnya kita mau mengambil data apa saja, menggunakan method get.
