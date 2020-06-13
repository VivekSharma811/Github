package com.lightstone.github.model.network

import com.lightstone.github.model.network.interceptor.ConnectivityInterceptor
import com.lightstone.github.model.response.GithubRepository
import com.lightstone.github.model.response.UserItem
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ) : GithubApiService {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubApiService::class.java)
        }
    }

    @GET("users")
    fun getUser() : Single<List<UserItem>>

    @GET("users/{username}/repos")
    fun getRepository(
        @Path("username") username : String
    ) : Single<List<GithubRepository>>

}