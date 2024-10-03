package com.group.githubfinder.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class FavoriteUser(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "login")
    var login: String = "",

    @ColumnInfo(name = "avatarUrl")
    @SerializedName("avatar_url")
    var avatarUrl: String = ""
) : Parcelable
