package com.disqo.task.presentation.starredUsers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.disqo.task.R
import com.disqo.task.databinding.ItemRecyclerViewUserBinding
import com.disqo.task.presentation.starredUsers.view_model.UserViewModel

class UsersAdapter(private val onNextPageListener: OnNextPageListener) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private val userViewModels: MutableList<UserViewModel> = mutableListOf()

    class UserViewHolder(private val binding: ItemRecyclerViewUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(userViewModel: UserViewModel) {
            binding.usernameTextView.text = userViewModel.login
            binding.avatarImageView.load(userViewModel.avatarUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding: ItemRecyclerViewUserBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_recycler_view_user,
                parent,
                false
            )

        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindData(userViewModels[holder.adapterPosition])

        if (position == userViewModels.size - 2) {
            onNextPageListener.onNextPage()
        }
    }

    override fun getItemCount(): Int {
        return userViewModels.size
    }

    internal fun addUsers(users: MutableList<UserViewModel>) {
        var index = 0
        if (userViewModels.size > 0) {
            index = userViewModels.size - 1
        }
        userViewModels.addAll(index, users)
        notifyItemRangeInserted(index, users.size)
    }
}