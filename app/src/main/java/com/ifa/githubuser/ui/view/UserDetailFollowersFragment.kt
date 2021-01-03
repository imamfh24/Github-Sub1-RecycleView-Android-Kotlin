package com.ifa.githubuser.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ifa.githubuser.R
import com.ifa.githubuser.databinding.UserDetailFollowersFragmentBinding
import com.ifa.githubuser.ui.adapter.UserFollowAdapter
import com.ifa.githubuser.ui.viewmodel.UserDetailFollowersViewModel
import java.util.*
import kotlin.concurrent.schedule

class UserDetailFollowersFragment : Fragment() {

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_SECTION_USER = "section_user"

        fun newInstance(index: Int, user: String?) = UserDetailFollowersFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_SECTION_NUMBER, index)
                putString(ARG_SECTION_USER, user)
            }
        }
    }

    private var _binding: UserDetailFollowersFragmentBinding? = null
    private val binding get() = _binding!!
    private var _adapter: UserFollowAdapter? = null
    private val adapter get() = _adapter!!

    private lateinit var viewModel: UserDetailFollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserDetailFollowersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val user = arguments?.getString(ARG_SECTION_USER)

        setupUI()

        setupViewModel(index, user)
        setupObserve(index)
    }

    private fun setupUI() {
        //Adapter
        _adapter = UserFollowAdapter()
        adapter.notifyDataSetChanged()
        //Recycle View
        binding.rvUserDetailFollow.layoutManager = LinearLayoutManager(context)
        binding.rvUserDetailFollow.setHasFixedSize(true)
        binding.rvUserDetailFollow.adapter = adapter
    }

    private fun setupViewModel(index: Int?, user: String?) {
        viewModel = ViewModelProvider(this).get(UserDetailFollowersViewModel::class.java)
        showLoading(true)
        Timer().schedule(1000){
            when(index){
                0 -> viewModel.setListUserDetailFollower(user!!)
                1 -> viewModel.setListUserDetailFollowing(user!!)
            }
        }
    }

    private fun setupObserve(index: Int?) {
        when(index){
            0 -> {
                viewModel.getListUserDetailFollower().observe(this, { listFollowers ->
                    Log.d("ShowNoData", "" + listFollowers.toString())
                    if (listFollowers.isNullOrEmpty()){
                        showNoData(true, index)
                    } else {
                        adapter.userFollow(listFollowers)
                        showLoading(false)
                    }
                })
            }
            1 -> {
                viewModel.getListUserDetailFollowing().observe(this, {listFollowing ->
                    if (listFollowing.isNullOrEmpty()){
                        showNoData(true, index)
                    } else {
                        adapter.userFollow(listFollowing)
                        showLoading(false)
                    }
                })
            }
        }

    }

    private fun showNoData(state: Boolean, index: Int){
        if (state){
            when(index){
                0 -> binding.layoutNoDataFollow.tvItemNoData.text = resources.getString(R.string.no_followers)
                1 -> binding.layoutNoDataFollow.tvItemNoData.text = resources.getString(R.string.no_following)
            }
            binding.layoutNoDataFollow.layoutNoData.visibility = View.VISIBLE
            binding.rvUserDetailFollow.visibility = View.GONE
            binding.progressBarFollow.visibility = View.GONE
        } else {
            binding.layoutNoDataFollow.layoutNoData.visibility = View.GONE
            binding.rvUserDetailFollow.visibility = View.VISIBLE
            binding.progressBarFollow.visibility = View.GONE
        }
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBarFollow.visibility = View.VISIBLE
            binding.rvUserDetailFollow.visibility = View.GONE
            binding.layoutNoDataFollow.layoutNoData.visibility = View.GONE

        } else {
            binding.progressBarFollow.visibility = View.GONE
            binding.rvUserDetailFollow.visibility = View.VISIBLE
            binding.layoutNoDataFollow.layoutNoData.visibility = View.GONE
        }
    }
}