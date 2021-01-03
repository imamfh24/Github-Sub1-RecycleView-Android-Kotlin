package com.ifa.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ifa.githubuser.ItemClickListener
import com.ifa.githubuser.R
import com.ifa.githubuser.data.model.User
import com.ifa.githubuser.data.model.UserDetail
import com.ifa.githubuser.databinding.ItemRowUserBinding

class UserSearchAdapter : RecyclerView.Adapter<UserSearchAdapter.ViewHolder>() {

    private val mUserSearchData = ArrayList<User>()
    private val mUserDetail = ArrayList<UserDetail>()

    private lateinit var itemClickListener: ItemClickListener

    fun searchDataUser(dataUser: ArrayList<User>, userDetailData: ArrayList<UserDetail>){
        mUserSearchData.clear()
        mUserDetail.clear()
        mUserSearchData.addAll(dataUser)
        mUserDetail.addAll(userDetailData)

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
        holder.bindDetail(mUserDetail[position])
        holder.itemView.setOnClickListener{
            itemClickListener.onItemClick(mUserDetail[position])
        }
    }

    override fun getItemCount(): Int = mUserSearchData.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowUserBinding.bind(itemView)

        fun bindDetail(userDetail: UserDetail) {
            with(itemView){
                binding.tvSearchUsername.text = userDetail.login
                binding.tvSearchNameUser.text = if (userDetail.name.isNullOrBlank()) resources.getString(R.string.line) else userDetail.name
                binding.tvSearchUserFollowers.text = userDetail.followers.toString()
                binding.tvSearchUserFollowing.text = userDetail.following.toString()
                Glide.with(itemView.context)
                    .load(userDetail.avatarUrl)
                    .apply(RequestOptions())
                    .into(binding.imgSearchPhotoUser)
            }
        }
    }
}