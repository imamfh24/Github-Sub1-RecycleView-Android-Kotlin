package com.ifa.githubuser.data.api

import com.ifa.githubuser.data.model.UserDetail
import com.ifa.githubuser.data.model.UserSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 3100c2a536a670dbc9742278de338e56db073d07")
    fun searchUser(@Query("q") username: String): Call<UserSearchResponse?>

    @GET("users/{user}")
    @Headers("Authorization: token 3100c2a536a670dbc9742278de338e56db073d07")
    fun getUsers(@Path("user") user: String): Call<UserDetail?>

}