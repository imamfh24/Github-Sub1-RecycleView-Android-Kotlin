package com.ifa.githubuser

import com.ifa.githubuser.data.model.User

interface ItemClickListener {
    fun onItemClick(data: User)
}