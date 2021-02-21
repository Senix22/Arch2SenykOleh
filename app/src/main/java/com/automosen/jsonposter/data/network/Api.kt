package com.automosen.jsonposter.data.network

import com.automosen.jsonposter.data.db.entities.Post
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {


    @POST("posts")
    suspend fun addPost(
        @Body name : Post
    ) : Response<Post>

    @GET("posts")
    suspend fun getPosts() : Response<List<Post>>

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor) : Api{

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }
}