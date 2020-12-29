package com.ifa.githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifa.githubuser.data.api.RetrofitBuilder
import com.ifa.githubuser.data.model.ItemsItem
import com.ifa.githubuser.data.model.UserSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<ItemsItem>>()

    fun getListUsers() : LiveData<ArrayList<ItemsItem>>{
        return listUsers
    }

    fun setListUsers(userName: String){

        val searchCall: Call<UserSearchResponse?> = RetrofitBuilder.apiService.searchUser(userName)

        searchCall.enqueue(object : Callback<UserSearchResponse?> {
            override fun onResponse(
                call: Call<UserSearchResponse?>,
                response: Response<UserSearchResponse?>
            ) {
                if (response.isSuccessful && response.body() != null){
                    if (response.body()!!.totalCount == 0 ) {
                        listUsers.value = null
                    } else {
                        listUsers.value = response.body()!!.items as ArrayList<ItemsItem>

                        Log.d("size: ", listUsers.value!![0].login!!)
                        /*for (i in 0 until listUsers.value!!.size){
                            val userItems = listUsers.value!![i]
                        }*/
                    }
                }
            }

            override fun onFailure(call: Call<UserSearchResponse?>, t: Throwable) {
                listUsers.value = null
            }

        })
    }
}