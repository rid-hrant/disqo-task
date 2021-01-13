package com.disqo.task.presentation.starredUsers.view_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserViewModel(
    val login: String,
    val avatarUrl: String
) : Parcelable