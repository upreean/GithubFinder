package com.group.githubfinder.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.group.githubfinder.data.database.FavoriteUser
import com.group.githubfinder.databinding.FragmentFavoriteBinding
import com.group.githubfinder.di.Injection
import com.group.githubfinder.ui.DetailActivity
import com.group.githubfinder.ui.DetailViewModelFactory
import com.group.githubfinder.ui.adapter.FavoriteAdapter

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var adapter: FavoriteAdapter
    private lateinit var viewModel: FavoriteViewModel

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val repository = Injection.provideRepository(requireActivity().application)
        val detailViewModelFactory = DetailViewModelFactory(repository)
        viewModel = ViewModelProvider(this, detailViewModelFactory).get(FavoriteViewModel::class.java)

        adapter = FavoriteAdapter(viewModel)

        binding.recyclerViewFavorite.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavoriteFragment.adapter
        }

        viewModel.getAllFavoriteUsers().observe(viewLifecycleOwner) { users ->
            val favorites = arrayListOf<FavoriteUser>()
            users.map {
                val favorite = FavoriteUser(login = it.login, avatarUrl = it.avatarUrl)
                favorites.add(favorite)
            }
            adapter.submitList(favorites)
        }

        adapter.setOnItemClickListener { favoriteUser ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("username", favoriteUser.login)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}