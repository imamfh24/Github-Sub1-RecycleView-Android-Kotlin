package com.ifa.githubuser.ui.view

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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

        setSupportActionBar(binding.mainActivityToolbar)
        setupUI()
        setupViewModel()
        setupObserve()
    }

    private fun setupUI() {
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter

        setupSearchView()
    }

    private fun setupSearchView() {
        //SearchView
        val searchView = binding.etSearchView

        /*searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count >= 3) {
                    mainViewModel.setListUsers(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })*/

        searchView.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mainViewModel.setListUsers(binding.etSearchView.text.toString())
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
                adapter.searchDataUser(listUsers)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}