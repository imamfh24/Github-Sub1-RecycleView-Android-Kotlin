package com.ifa.githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifa.githubuser.data.api.RetrofitBuilder
import com.ifa.githubuser.data.model.ItemsItem
import com.ifa.githubuser.data.model.UserDetail
import com.ifa.githubuser.data.model.UserSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<ItemsItem>>()
    val detailUsersLiveData = MutableLiveData<ArrayList<UserDetail>>()

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
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.totalCount == 0) {
                        detailUsersLiveData.value = null
                        listUsers.value = null
                    } else {
                        listUsers.value = response.body()!!.items as ArrayList<ItemsItem>

                        //Log.d("listUsers ", listUsers.value.toString())

                        setDetailUsers(listUsers.value!!)
                    }
                }
            }

            override fun onFailure(call: Call<UserSearchResponse?>, t: Throwable) {
                listUsers.value = null
            }

        })
    }

    fun getDetailUsers(): LiveData<ArrayList<UserDetail>>{
        return detailUsersLiveData
    }

    fun setDetailUsers(listUsers: ArrayList<ItemsItem>){
        Log.d("ListUsers: ", listUsers.toString())
        val userDetail: ArrayList<UserDetail> = ArrayList()

        try {
            for (i: ItemsItem in listUsers){
                val userCall: Call<UserDetail?> = RetrofitBuilder.apiService.getUsers(i.login.toString())
                Log.d("userPerItem", ""+ i.login.toString())
                userCall.enqueue(object : Callback<UserDetail?> {
                    override fun onResponse(call: Call<UserDetail?>, response: Response<UserDetail?>) {
                        if (response.isSuccessful && response.body() != null) {
                            Log.d("userPerResponse: ",  " "+ i.login.toString()+ " " + response.body()!!.name)
                            userDetail.add(response.body()!!)
                            Log.d("userDetail: ", "" + userDetail.toString())
                            if (userDetail.size == listUsers.size){
                                detailUsersLiveData.value = userDetail
//                                Log.d("userListDetail: ", "" + detailUsersLiveData.value.toString())
                            }
                        }
                    }

                    override fun onFailure(call: Call<UserDetail?>, t: Throwable) {
                        detailUsersLiveData.value = null
                    }
                })
            }

        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}