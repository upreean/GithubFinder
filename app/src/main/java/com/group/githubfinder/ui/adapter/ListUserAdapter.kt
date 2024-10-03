package com.group.githubfinder.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.group.githubfinder.data.response.ItemsItem
import com.group.githubfinder.databinding.ListUserBinding
import com.group.githubfinder.ui.adapter.ListUserAdapter.MyViewHolder.Companion.DIFF_CALLBACK

class ListUserAdapter : ListAdapter<ItemsItem, ListUserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var onItemClickListener: ((ItemsItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ItemsItem) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserAdapter.MyViewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListUserAdapter.MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(user)
        }
    }

    class MyViewHolder(val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .into(binding.imgUser)
            binding.tvUsername.text = user.login
        }

        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
                override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}