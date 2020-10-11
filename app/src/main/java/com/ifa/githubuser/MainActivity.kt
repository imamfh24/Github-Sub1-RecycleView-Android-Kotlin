package com.ifa.githubuser

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvUser: RecyclerView

    private lateinit var dataUsername: Array<String>
    private lateinit var dataName: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataRepository: Array<String>
    private lateinit var dataCompany: Array<String>
    private lateinit var dataFollowers: Array<String>
    private lateinit var dataFollowing: Array<String>
    private lateinit var dataAvatar: TypedArray

    private var users = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

        showRecycleList()

    }

    private fun createRecycleDividerLine() {
        val divider: RecyclerView.ItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        rvUser.addItemDecoration(divider)
    }

    private fun showRecycleList() {
        createRecycleDividerLine()
        addItem()
        rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = UserAdapter(users)
        rvUser.adapter = listUserAdapter

        listUserAdapter.setItemClickListener(object : ItemClickListener{
            override fun onItemClick(data: User) {
                showSelected(data)
            }
        })
    }

    private fun addItem(){
        prepare()
        for (position in dataUsername.indices){
            val user = User(
                dataUsername[position],
                dataName[position],
                dataLocation[position],
                dataRepository[position],
                dataCompany[position],
                dataFollowers[position],
                dataFollowing[position],
                dataAvatar.getResourceId(position, -1)
            )
            users.add(user)
        }
    }

    private fun prepare(){
        dataUsername = resources.getStringArray(R.array.username)
        dataName = resources.getStringArray(R.array.name)
        dataLocation = resources.getStringArray(R.array.location)
        dataRepository = resources.getStringArray(R.array.repository)
        dataCompany = resources.getStringArray(R.array.company)
        dataFollowers = resources.getStringArray(R.array.followers)
        dataFollowing = resources.getStringArray(R.array.following)
        dataAvatar = resources.obtainTypedArray(R.array.avatar)
    }

    fun showSelected(user: User){
        Toast.makeText(this, user.name, Toast.LENGTH_SHORT).show()
        val moveToUserDetail = Intent(this, UserDetailActivity::class.java)
        moveToUserDetail.putExtra(UserDetailActivity.USER_DETAIL, user)
        startActivity(moveToUserDetail)
    }
}