package com.ifa.githubuser.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ifa.githubuser.ItemClickListener
import com.ifa.githubuser.R
import com.ifa.githubuser.data.model.ItemsItem
import com.ifa.githubuser.data.model.UserDetail
import com.ifa.githubuser.databinding.ItemRowUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val mUserSearchData = ArrayList<ItemsItem>()
    private val mUserDetail = ArrayList<UserDetail>()

    private lateinit var itemClickListener: ItemClickListener

    fun searchDataUser(dataUser: ArrayList<ItemsItem>, userDetailData: ArrayList<UserDetail>){
        mUserSearchData.clear()
        mUserDetail.clear()
        mUserSearchData.addAll(dataUser)
        Log.d("mUserSearchData ", "" + mUserSearchData.size.toString())
        mUserDetail.addAll(userDetailData)
        Log.d("mUserDetail ", "" + mUserDetail.size.toString())

        if (mUserSearchData.size == mUserDetail.size){
            notifyDataSetChanged()
        }
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("mUserPosition:", "" + position)
        holder.bind(mUserSearchData[position])
        holder.bindDetail(mUserDetail[position])
        /*holder.itemView.setOnClickListener{
            itemClickListener.onItemClick(users[holder.adapterPosition])
        }*/
    }

    override fun getItemCount(): Int = mUserSearchData.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowUserBinding.bind(itemView)

        fun bind (userItems: ItemsItem){
            with(itemView){
                binding.tvSearchUsername.text = userItems.login
                /*val userCall: Call<UserDetail?> = RetrofitBuilder.apiService.getUsers(userItems.login!!)
                userCall.enqueue(object : Callback<UserDetail?>{
                    override fun onResponse(
                        call: Call<UserDetail?>,
                        response: Response<UserDetail?>
                    ) {
                        val followers = response.body()?.followers
                        val following = response.body()?.following
                        val name = response.body()?.name

                        binding.tvSearchNameUser.text = name
                        binding.tvSearchUserFollowing.text = following.toString()
                        binding.tvSearchUserFollowers.text = followers.toString()

                    }

                    override fun onFailure(call: Call<UserDetail?>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })*/
                Glide.with(itemView.context)
                    .load(userItems.avatarUrl)
                    .apply(RequestOptions())
                    .into(binding.imgSearchPhotoUser)
            }
        }
        fun bindDetail(userDetail: UserDetail) {
            with(itemView){
                binding.tvSearchNameUser.text = userDetail.name
                binding.tvSearchUserFollowers.text = userDetail.followers.toString()
                binding.tvSearchUserFollowing.text = userDetail.following.toString()
            }
        }
    }
}