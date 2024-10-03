package com.group.githubfinder.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.group.githubfinder.data.database.FavoriteUser
import com.group.githubfinder.databinding.ListUserBinding
import com.group.githubfinder.ui.favorite.FavoriteViewModel

class FavoriteAdapter(private val viewModel: FavoriteViewModel) : ListAdapter<FavoriteUser, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var onItemClickListener: ((FavoriteUser) -> Unit)? = null

    fun removeFavorite(position: Int) {
        val favoriteUser = getItem(position)
        viewModel.delete(favoriteUser)
    }

    fun setOnItemClickListener(listener: (FavoriteUser) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val favoriteUser = getItem(position)
        holder.bind(favoriteUser)

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(favoriteUser)

        }
    }

    class MyViewHolder(val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUser) {
            Glide.with(binding.root)
                .load(favoriteUser.avatarUrl)
                .into(binding.imgUser)
            binding.tvUsername.text = favoriteUser.login
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUser>() {
            override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem.login == newItem.login
            }

            override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }
        }
    }
}