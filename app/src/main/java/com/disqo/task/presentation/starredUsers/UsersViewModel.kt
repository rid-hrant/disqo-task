package com.disqo.task.presentation.starredUsers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.disqo.task.presentation.starredUsers.view_model.UserViewModel
import com.disqo.task.repository.UsersRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.dsl.module

val viewModelModule = module {
    factory { UsersViewModel(get()) }
}

class UsersViewModel(private val repository: UsersRepository) : ViewModel() {

    internal val userViewModel: MutableLiveData<List<UserViewModel>> = MutableLiveData()
    internal val error: MutableLiveData<Throwable> = MutableLiveData()
    private var page: Int = 0
    private var loading: Boolean = false

    fun getUsers() {
        if (loading) return

        loading = true
        viewModelScope.launch {
            repository.getUsers(page)
                .catch { e ->
                    e.printStackTrace()
                    error.value = e
                    loading = false
                }
                .collect { userViewModels ->
                    userViewModel.value = userViewModels
                    page++
                    loading = false
                }
        }
    }
}