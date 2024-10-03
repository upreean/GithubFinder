package com.group.githubfinder.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.group.githubfinder.data.database.FavoriteUser
import com.group.githubfinder.data.repository.FavoriteRepository
import com.group.githubfinder.data.response.DetailUserResponse
import com.group.githubfinder.data.response.ItemsItem
import com.group.githubfinder.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (private val repository: FavoriteRepository) : ViewModel() {

    private val _selectedUser = MutableLiveData<DetailUserResponse>()
    val selectedUser: LiveData<DetailUserResponse> = _selectedUser

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun insertFavoriteUser(favoriteUser: FavoriteUser) {
        repository.insertFavorite(favoriteUser)
    }

    fun deleteFavoriteUser(favoriteUser: FavoriteUser) {
        repository.deleteFavorite(favoriteUser)
    }

    fun getOneFavoriteUser(login: String): LiveData<FavoriteUser> {
        return repository.getOneFavoriteUser(login)
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun findDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _selectedUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}
