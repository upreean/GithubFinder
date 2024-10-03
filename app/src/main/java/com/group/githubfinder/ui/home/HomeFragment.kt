package com.group.githubfinder.ui.home

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.group.githubfinder.databinding.FragmentHomeBinding
import com.group.githubfinder.ui.DetailActivity
import com.group.githubfinder.ui.adapter.ListUserAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ListUserAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = ListUserAdapter()
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUser.layoutManager = layoutManager
        binding.rvUser.adapter = adapter

        getInitialUsers()

        adapter.setOnItemClickListener { user ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("username", user.login)
            startActivity(intent)
        }

        return root
    }

    private fun getInitialUsers() {
        viewModel.fetchUser("a")
        viewModel.items.observe(viewLifecycleOwner, { items ->
            Log.d(TAG, "List size: ${items?.size}")
            adapter.submitList(items)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}