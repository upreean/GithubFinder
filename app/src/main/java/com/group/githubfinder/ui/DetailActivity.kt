package com.group.githubfinder.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import com.group.githubfinder.R
import com.group.githubfinder.data.database.FavoriteUser
import com.group.githubfinder.databinding.ActivityDetailBinding
import com.group.githubfinder.di.Injection
import com.group.githubfinder.ui.adapter.SectionPageAdapter

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var sectionsPagerAdapter: SectionPageAdapter

    companion object {
        private const val TAG = "DetailActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")

        val repository = Injection.provideRepository(application)
        val detailViewModelFactory = DetailViewModelFactory(repository)
        viewModel = ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel::class.java)


        if (username != null) {
            viewModel.findDetailUser(username)
        }

        viewModel.selectedUser.observe(this) { user ->
            if (user != null) {
                showLoading(true)
                Log.d(TAG, "Received user data: $user")
                Picasso.get().load(user.avatarUrl).into(binding.imgUserPhoto)
                binding.tvUsername.text = user.login
                binding.tvName.text = user.name
                val followersText = "${user.followers} Followers"
                binding.tvFollower.text = followersText
                binding.tvFollowing.text = "${user.following} Following"
                viewModel.getOneFavoriteUser(user.login).observe(this) { favoriteUser ->
                    if (favoriteUser == null) {
                        binding.fabFavorite.setImageResource(R.drawable.favoritenoborder)
                    } else {
                        binding.fabFavorite.setImageResource(R.drawable.favorite)
                    }
                    binding.fabFavorite.setOnClickListener {
                        if (favoriteUser == null) {
                            viewModel.insertFavoriteUser(FavoriteUser(user.login, user.avatarUrl))
                        } else {
                            viewModel.deleteFavoriteUser(favoriteUser)
                        }
                    }
                }
            } else {
                Log.e(TAG, "Failed to receive user data.")
            }
        }

        sectionsPagerAdapter = SectionPageAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = if (position == 0) "Followers" else "Following"
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}
