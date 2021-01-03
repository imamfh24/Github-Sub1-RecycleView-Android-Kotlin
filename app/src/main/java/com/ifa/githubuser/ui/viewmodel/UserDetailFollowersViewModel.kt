package com.ifa.githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifa.githubuser.data.api.RetrofitBuilder
import com.ifa.githubuser.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailFollowersViewModel : ViewModel() {
    val listUserDetailFollower = MutableLiveData<ArrayList<User>>()
    val listUserDetailFollowing = MutableLiveData<ArrayList<User>>()

    fun getListUserDetailFollower(): LiveData<ArrayList<User>> {
        return listUserDetailFollower
    }

    fun getListUserDetailFollowing(): LiveData<ArrayList<User>> {
        return listUserDetailFollowing
    }

    fun setListUserDetailFollower(username: String){
        val userFollower: Call<List<User?>> = RetrofitBuilder.apiService.getFollowersUser(username)
        Log.d("CallFollower", "testCall")
        userFollower.enqueue(object: Callback<List<User?>>{
            override fun onResponse(
                call: Call<List<User?>>,
                response: Response<List<User?>>
            ) {
                if (response.isSuccessful && response.body() != null){
                    listUserDetailFollower.value = response.body() as ArrayList<User>
                } else {
                    listUserDetailFollowing.value = null
                }
            }

            override fun onFailure(call: Call<List<User?>>, t: Throwable) {
                listUserDetailFollowing.value = null
            }

        })
    }

    fun setListUserDetailFollowing(username: String){
        val userFollowing: Call<List<User?>> = RetrofitBuilder.apiService.getFollowingUser(username)
        Log.d("CallFollowing", "testCall")
        userFollowing.enqueue(object: Callback<List<User?>>{
            override fun onResponse(
                call: Call<List<User?>>,
                response: Response<List<User?>>
            ) {
                if (response.isSuccessful && response.body() != null){
                    listUserDetailFollowing.value = response.body() as ArrayList<User>
                } else {
                    listUserDetailFollowing.value = null
                }
            }

            override fun onFailure(call: Call<List<User?>>, t: Throwable) {
                listUserDetailFollowing.value = null
            }

        })
    }
}