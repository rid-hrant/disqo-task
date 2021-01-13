package com.disqo.task

import android.app.Application
import com.disqo.task.networking.networkingModule
import com.disqo.task.presentation.starredUsers.viewModelModule
import com.disqo.task.repository.repoModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class UsersApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@UsersApp)
            modules(
                listOf(
                    networkingModule,
                    repoModule,
                    viewModelModule
                )
            )
        }
    }
}