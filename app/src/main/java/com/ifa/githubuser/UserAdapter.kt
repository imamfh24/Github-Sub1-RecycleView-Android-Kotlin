package com.ifa.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class UserAdapter(private val users: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .apply(RequestOptions())
            .into(holder.imgPhoto)

        holder.tvName.text = user.name
        holder.tvUsername.text = user.username
        holder.itemView.setOnClickListener{
            itemClickListener.onItemClick(users[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = users.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_photo)

    }
}