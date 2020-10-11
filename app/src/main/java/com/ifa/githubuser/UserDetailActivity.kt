package com.ifa.githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity() {

    companion object{
        const val USER_DETAIL = "user_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        setView()
    }

    private fun setView() {
        val userDetail = intent.getParcelableExtra<User>(USER_DETAIL)
        userDetail?.avatar?.let { img_user_detail_avatar.setImageResource(it) }
        tv_user_detail_name.text = userDetail?.name
        tv_user_detail_username.text = userDetail?.username
        tv_user_detail_followers.text = userDetail?.followers
        tv_user_detail_following.text = userDetail?.following
        tv_user_detail_location.text = userDetail?.location
        tv_user_detail_company.text = userDetail?.company
        tv_user_detail_repository.text = userDetail?.repository

        if (supportActionBar != null) {
            supportActionBar!!.title = "Detail User"
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}