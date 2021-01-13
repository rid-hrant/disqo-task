package com.disqo.task.repository

import com.disqo.task.presentation.starredUsers.view_model.UserViewModel
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getUsers(page: Int): Flow<List<UserViewModel>>
}