package com.group.githubfinder.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.group.githubfinder.data.response.GithubResponse
import com.group.githubfinder.data.response.ItemsItem
import com.group.githubfinder.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _items = MutableLiveData<List<ItemsItem>>()
    val items: LiveData<List<ItemsItem>> = _items

    companion object {
        private const val TAG = "HomeViewModel"
    }

    fun fetchUser(username: String) {
        val client = ApiConfig.getApiService().getSearchUser(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(call: Call<GithubResponse>, response: Response<GithubResponse>) {
                if (response.isSuccessful) {
                    _items.value = response.body()?.items
                    Log.d(TAG, "onResponse: Successful")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}