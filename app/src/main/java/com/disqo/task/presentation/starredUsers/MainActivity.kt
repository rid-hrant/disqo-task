package com.disqo.task.presentation.starredUsers

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.disqo.task.R
import com.disqo.task.databinding.ActivityMainBinding
import com.disqo.task.presentation.starredUsers.view_model.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val usersAdapter: UsersAdapter = UsersAdapter(object : OnNextPageListener{
        override fun onNextPage() {
            usersViewModel.getUsers()
        }

    })
    private val usersViewModel: UsersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        usersRecyclerView.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(context)
        }

        usersViewModel.userViewModel.observe(this, {
            usersAdapter.addUsers(it as MutableList<UserViewModel>)
        })

        usersViewModel.error.observe(this, {
            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
        })

        usersViewModel.getUsers()
    }

    override fun onDestroy() {
        super.onDestroy()
        usersViewModel.userViewModel.removeObservers(this)
    }
}