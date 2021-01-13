package com.disqo.task.repository

import com.disqo.task.networking.RepoAPIInterface
import com.disqo.task.networking.dto.UserDto
import com.disqo.task.presentation.starredUsers.view_model.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.dsl.module

val repoModule = module {
    single {
        @Suppress("USELESS_CAST")
        UserRepositoryImpl(get()) as UsersRepository
    }
}

class UserRepositoryImpl(private val repoAPIInterface: RepoAPIInterface) : UsersRepository {

    override fun getUsers(page: Int): Flow<List<UserViewModel>> {
        return flow {
            val stargazers = repoAPIInterface.getStargazers(
                "square",
                "retrofit",
                20,
                page
            )
                .map { data -> mapObject(data) }
            emit(stargazers)
        }.flowOn(Dispatchers.IO)
    }

    // TODO: 1/13/21 move to Mapper class
    private fun mapObject(data: UserDto): UserViewModel {
        return UserViewModel(
            data.login,
            data.avatarUrl
        )
    }
}