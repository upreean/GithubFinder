package com.group.githubfinder.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.group.githubfinder.data.response.ItemsItem
import com.group.githubfinder.databinding.FragmentFollowBinding
import com.group.githubfinder.di.Injection
import com.group.githubfinder.ui.adapter.ListUserAdapter

class FollowFragment : Fragment() {
    private var position: Int = 0
    private lateinit var binding: FragmentFollowBinding
    private lateinit var detailViewModel: DetailViewModel
    private var username: String = ""

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val layoutManager = LinearLayoutManager(activity)
        binding.rvDetailUser.layoutManager = layoutManager

        val application = requireActivity().application
        val repository = Injection.provideRepository(application)
        val detailViewModelFactory = DetailViewModelFactory(repository)
        detailViewModel = ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel::class.java)


        detailViewModel.following.observe(viewLifecycleOwner) { following ->
            setDataUser(following)
        }

        detailViewModel.followers.observe(viewLifecycleOwner) { followers ->
            setDataUser(followers)
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        val position = arguments?.getInt(ARG_POSITION)
        val username = arguments?.getString(ARG_USERNAME).toString()

        showLoading(true)
        if (position == 1) {
            showLoading(false)
            detailViewModel.getFollowing(username)
        } else {
            showLoading(false)
            detailViewModel.getFollowers(username)
        }
    }

    private fun setDataUser(dataItem: List<ItemsItem>) {
        val listUserAdapter = ListUserAdapter()
        listUserAdapter.submitList(dataItem)
        binding.rvDetailUser.adapter = listUserAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}