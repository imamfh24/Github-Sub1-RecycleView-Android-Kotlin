package com.ifa.githubuser.ui.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ifa.githubuser.ItemClickListener
import com.ifa.githubuser.data.model.UserDetail
import com.ifa.githubuser.databinding.ActivityMainBinding
import com.ifa.githubuser.ui.adapter.UserAdapter
import com.ifa.githubuser.ui.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupViewModel()
        setupObserve()
    }

    private fun setupUI() {
        //Toolbar
        setSupportActionBar(binding.mainActivityToolbar)

        //Adapter
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        //RecycleView
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter

        //Localization
        val menuLocalization = binding.menuLocalization
        menuLocalization.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }

        //SearchView
        setupSearchView()
    }

    private fun setupSearchView() {
        //SearchView
        val searchView = binding.etSearchView
        showLayoutData(true)

        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count == 0) {
                    showLoading(true)
                    showLoading(false)
                    showError(false)
                    showLayoutData(true)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        searchView.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val contents = binding.etSearchView.text.toString()
                showLayoutData(false)
                showError(false)
                showLoading(true)
                if (contents.isNotEmpty()){
                    mainViewModel.setListUsers(contents)
                } else {
                    showLayoutData(false)
                    showLoading(false)
                    showError(true)
                }

                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java
        )
    }

    private fun setupObserve() {
        mainViewModel.getListUsers().observe(this, { listUsers ->
            if (listUsers != null) {
                mainViewModel.getDetailUsers().observe(this, {detailUser ->
                    if (detailUser != null && listUsers.size == detailUser.size){
                        showLoading(true)
                        adapter.searchDataUser(listUsers, detailUser)
                        adapter.setItemClickListener(object : ItemClickListener{
                            override fun onItemClick(data: UserDetail) {
                                showSelectedData(data)
                            }
                        })
                        showLoading(false)
                    }
                })
            } else {
                showError(true)
                showLoading(false)
            }
        })
    }

    private fun showSelectedData(userDetail: UserDetail) {
        val moveToUserDetail = Intent(this, UserDetailActivity::class.java)
        moveToUserDetail.putExtra(UserDetailActivity.USER_DETAIL, userDetail)
        startActivity(moveToUserDetail)
    }

    private fun showError(state: Boolean) {
        if (state){
            binding.layoutUserNotFound.layoutUserNotFound.visibility = View.VISIBLE
            binding.rvUser.visibility = View.INVISIBLE
        } else {
            binding.layoutUserNotFound.layoutUserNotFound.visibility = View.GONE
        }
    }

    private fun showLayoutData(state: Boolean) {
        if (state){
            binding.layoutNoData.layoutNoData.visibility = View.VISIBLE
            binding.rvUser.visibility = View.INVISIBLE
        } else {
            binding.layoutNoData.layoutNoData.visibility = View.GONE
        }
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
            binding.rvUser.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE
        }
    }
}