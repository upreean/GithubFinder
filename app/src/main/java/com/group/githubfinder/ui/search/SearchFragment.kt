package com.group.githubfinder.ui.search

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.group.githubfinder.R
import com.group.githubfinder.data.response.GithubResponse
import com.group.githubfinder.data.retrofit.ApiConfig
import com.group.githubfinder.databinding.FragmentSearchBinding
import com.group.githubfinder.ui.DetailActivity
import com.group.githubfinder.ui.adapter.ListUserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        adapter = ListUserAdapter()

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUser.layoutManager = layoutManager
        binding.rvUser.adapter = adapter

        setupSearchView()

        getInitialUsers()

        adapter.setOnItemClickListener { user ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("username", user.login)
            startActivity(intent)
        }
    }

    private fun setupSearchView() {
        val editText = binding.editText

        editText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                performSearch()
                true
            } else {
                false
            }
        }

        editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.searchuser, 0)
        editText.setOnClickListener {
            performSearch()
        }

        viewModel.items.observe(viewLifecycleOwner, { items ->
            Log.d(TAG, "Received items: $items")
            adapter.submitList(items)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            Log.d(TAG, "Is loading: $isLoading")
            showLoading(isLoading)
        })
    }

    private fun performSearch() {
        val username = binding.editText.text.toString()
        Log.d(TAG, "Searching for username: $username")
        if (username.isNotEmpty()) {
            viewModel.searchUser(username)
        } else {
            Toast.makeText(requireContext(), "Username is empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getInitialUsers() {
        showLoading(true)
        val apiService = ApiConfig.getApiService()
        val call = apiService.getSearchUser("a")

        call.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                if (response.isSuccessful) {
                    val githubResponse = response.body()
                    if (githubResponse != null) {
                        val initialUsers = githubResponse.items
                        Log.d(TAG, "Initial users: $initialUsers")
                        adapter.submitList(initialUsers)
                        showLoading(false)
                    }
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}