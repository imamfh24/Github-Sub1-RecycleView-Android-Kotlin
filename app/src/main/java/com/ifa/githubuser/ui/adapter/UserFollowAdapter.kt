package com.ifa.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ifa.githubuser.R
import com.ifa.githubuser.data.model.User
import com.ifa.githubuser.databinding.ItemUserFollowBinding

class UserFollowAdapter : RecyclerView.Adapter<UserFollowAdapter.ViewHolder>() {

    private val mUserFollowListData = ArrayList<User>()

    fun userFollow(dataFollower: ArrayList<User>){
        mUserFollowListData.clear()
        mUserFollowListData.addAll(dataFollower)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user_follow, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mUserFollowListData[position])
    }

    override fun getItemCount(): Int = mUserFollowListData.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserFollowBinding.bind(itemView)

        fun bind(items: User){
            with(itemView){
                binding.tvUsernameFollow.text = items.login
                Glide.with(itemView.context)
                    .load(items.avatarUrl)
                    .apply(RequestOptions())
                    .into(binding.imgFollowPhotoUser)
            }
        }
    }
}