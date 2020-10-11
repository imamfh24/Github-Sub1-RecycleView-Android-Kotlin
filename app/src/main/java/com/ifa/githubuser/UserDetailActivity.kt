package com.ifa.githubuser

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream

class UserDetailActivity : AppCompatActivity() {

    companion object{
        const val USER_DETAIL = "user_detail"
    }

    private lateinit var tvUserDetailName: TextView
    private lateinit var tvUserDetailUsername: TextView
    private lateinit var tvUserDetailFollowers: TextView
    private lateinit var tvUserDetailFollowing: TextView
    private lateinit var tvUserDetailLocation: TextView
    private lateinit var tvUserDetailCompany: TextView
    private lateinit var tvUserDetailRepository: TextView
    private lateinit var imgUserDetailAvatar: ImageView

    private lateinit var userDetail: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        setView()
    }

    private fun setView() {
        userDetail = intent.getParcelableExtra(USER_DETAIL) as User
        imgUserDetailAvatar = findViewById(R.id.img_user_detail_avatar)
        userDetail.avatar?.let { imgUserDetailAvatar.setImageResource(it) }

        tvUserDetailName = findViewById(R.id.tv_user_detail_name)
        tvUserDetailName.text = userDetail.name

        tvUserDetailUsername = findViewById(R.id.tv_user_detail_username)
        tvUserDetailUsername.text = userDetail.username

        tvUserDetailFollowers = findViewById(R.id.tv_user_detail_followers)
        tvUserDetailFollowers.text = userDetail.followers

        tvUserDetailFollowing = findViewById(R.id.tv_user_detail_following)
        tvUserDetailFollowing.text = userDetail.following

        tvUserDetailLocation = findViewById(R.id.tv_user_detail_location)
        tvUserDetailLocation.text = userDetail.location

        tvUserDetailCompany = findViewById(R.id.tv_user_detail_company)
        tvUserDetailCompany.text = userDetail.company

        tvUserDetailRepository = findViewById(R.id.tv_user_detail_repository)
        tvUserDetailRepository.text = userDetail.repository

        if (supportActionBar != null) {
            supportActionBar!!.title = "Detail User"
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

                val drawable = imgUserDetailAvatar.drawable as BitmapDrawable
                val drawableName = (tvUserDetailName.text.toString() + ".png")
                val infoShare = "Hello There\n" +
                        "Share from github user\n" +
                        "Name: ${tvUserDetailName.text}\n" +
                        "From : ${tvUserDetailLocation.text}\n"

                createShareInfo(drawable, drawableName, infoShare)
            }
        }
    }

    private fun createShareInfo(
        drawable: BitmapDrawable,
        drawableName: String,
        infoShare: String
    ) {
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        val bitmap = drawable.bitmap

        val f = File(externalCacheDir.toString() + "/" + drawableName)

        val intent = Intent()

        try {
            val fileOutputStream = FileOutputStream(f)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)

            fileOutputStream.flush()
            fileOutputStream.close()

            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, infoShare)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f))
            intent.type = "image/*"
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        } catch (e: Exception) {

        }
        startActivity(Intent.createChooser(intent, "Share"))
    }
}