package com.ifa.githubuser

import com.ifa.githubuser.data.model.UserDetail

interface ItemClickListener {
    fun onItemClick(data: UserDetail)
}