package com.ifa.githubuser.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ifa.githubuser.R
import com.ifa.githubuser.data.model.UserDetail
import com.ifa.githubuser.databinding.ActivityUserDetailBinding
import com.ifa.githubuser.ui.adapter.UserDetailSectionsPageAdapter


class UserDetailActivity : AppCompatActivity() {

    companion object{
        const val USER_DETAIL = "user_detail"
    }

    private lateinit var binding: ActivityUserDetailBinding

    private lateinit var userDetail: UserDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
    }

    private fun setView() {
        setSupportActionBar(binding.userDetailActivityToolbar.toolbar)
        userDetail = intent.getParcelableExtra(USER_DETAIL) as UserDetail
        Glide.with(this)
            .load(userDetail.avatarUrl)
            .apply(RequestOptions())
            .into(binding.imgUserDetailAvatar)

        binding.tvUserDetailUsername.text = userDetail.login
        binding.tvUserDetailNameUser.text = if (userDetail.name.isNullOrBlank()) resources.getString(R.string.line) else userDetail.name
        binding.tvUserDetailFollowers.text = userDetail.followers.toString()
        binding.tvUserDetailFollowing.text = userDetail.following.toString()
        binding.tvUserDetailLocation.text = if (userDetail.location.isNullOrBlank()) resources.getString(R.string.line) else userDetail.location
        binding.tvUserDetailCompany.text = if (userDetail.company.isNullOrBlank()) resources.getString(R.string.line) else userDetail.company
        binding.tvUserDetailRepository.text = userDetail.publicRepos.toString()

        val sectionsPagerAdapter = UserDetailSectionsPageAdapter(this, supportFragmentManager, userDetail.login)
        val viewPager = binding.vpUserDetailFollowerFollowers
        viewPager.adapter = sectionsPagerAdapter

        val tabLayout = binding.tlUserDetailFollowerFollowers
        tabLayout.setupWithViewPager(viewPager)

        if (supportActionBar != null) {
            supportActionBar!!.title = resources.getString(R.string.user_detail)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_user_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(itemId: Int) {
        when(itemId){
            R.id.action_share_user_detail -> {

                val infoShare = "${userDetail.htmlUrl}\n" +
                        "${resources.getString(R.string.share_from)}\n" +
                        "${resources.getString(R.string.name)}: ${binding.tvUserDetailNameUser.text}\n" +
                        "${resources.getString(R.string.from)}: ${binding.tvUserDetailLocation.text}"

                createShareInfo(infoShare)
            }
        }
    }

    private fun createShareInfo(infoShare: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, infoShare)
        intent.type = "text/plain"
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(Intent.createChooser(intent, resources.getString(R.string.share)))
    }
}